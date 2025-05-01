package com.mateeooss.migrate_playlist.services;

import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.special.SearchResult;
import se.michaelthelin.spotify.model_objects.specification.Playlist;
import se.michaelthelin.spotify.requests.data.playlists.AddItemsToPlaylistRequest;
import se.michaelthelin.spotify.requests.data.playlists.CreatePlaylistRequest;

import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SpotifyService {
    private final SpotifyApi spotifyApi;

    public SpotifyService(@Value("${spotify.acesstoken}") String spotifyAccessToken) {
        this.spotifyApi = new SpotifyApi.Builder()
                .setAccessToken(spotifyAccessToken)
                .build();
    }

    public String createSpotifyPlaylist(String spotifyUserId, String playlistName){
        try {
            CreatePlaylistRequest request = spotifyApi.createPlaylist(spotifyUserId, playlistName).public_(false).build();
            Playlist playlist = request.execute();
            return playlist.getId();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SpotifyWebApiException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public String searchSpotifyTrack(Map<String, String> trackInfo) {
        String title = trackInfo.get("title");
        String channel = trackInfo.get("channel");
        String description = trackInfo.get("description");

        // Limpar título
        String cleanTitle = Normalizer.normalize(title, Normalizer.Form.NFD)
                .replaceAll("[\\p{InCombiningDiacriticalMarks}]", "") // Remove acentos
                .replaceAll("\\[.*?\\]|\\(.*?\\)|Official Video|Lyrics|Remix|MV|Audio|Lyric Video|FREE DOWNLOAD|HD|Explicit|\\d+\\.|\\p{Punct}|[\\u3000-\\u303F\\u4E00-\\u9FFF\\uFF00-\\uFFEF]", "") // Remove padrões e caracteres especiais
                .replaceAll("\\s+", " ") // Normaliza espaços
                .trim();

        // Extrair artista
        String artist = "";
        if (channel != null) {
            artist = channel.replaceAll("(?i)Official|Vevo|Music|- Topic|VEVO|\\d+", "") // Remove sufixos
                    .replaceAll("\\s+", " ")
                    .trim();
        }
        if (artist.isEmpty() && description != null && description.toLowerCase().contains("by")) {
            String[] parts = description.split("(?i)by");
            if (parts.length > 1) {
                artist = parts[1].split("[,\\n]")[0]
                        .replaceAll("(?i)Official|Vevo|Music|- Topic|VEVO|\\d+", "")
                        .replaceAll("\\s+", " ")
                        .trim();
            }
        }

        // Montar queries
        List<String> queries = new ArrayList<>();
        if (!artist.isEmpty()) {
            queries.add("track:" + cleanTitle + " artist:" + artist);
        }
        queries.add("track:" + cleanTitle); // Fallback sem artista

        System.out.println("query1: " + queries.getFirst());
        System.out.println("query2: " + queries.getLast());
        // Buscar
        String result = null;
        for (String query : queries) {
            SearchResult searchResult = null;
            try {
                searchResult = spotifyApi.searchItem(query, "track")
                        .limit(10)
                        .build()
                        .execute();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (SpotifyWebApiException e) {
                throw new RuntimeException(e);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            // Escolher a primeira faixa que corresponde aproximadamente
            for (var track : searchResult.getTracks().getItems()) {
                String spotifyTitle = Normalizer.normalize(track.getName(), Normalizer.Form.NFD)
                        .replaceAll("[\\p{InCombiningDiacriticalMarks}]", "")
                        .replaceAll("\\p{Punct}", "")
                        .replaceAll("\\s+", " ")
                        .trim()
                        .toLowerCase();
                // Simples fuzzy matching: verificar se o título contém a maioria das palavras
                if (spotifyTitle.contains(cleanTitle) || cleanTitle.contains(spotifyTitle)) {
                    result = track.getUri();
                    break;
                }
            }
            if (result != null) break;
        }

        if (result == null) {
            System.err.println("Não encontrou: " + title + " (Query: " + queries + ")");
        } else {
            System.out.println("✅ foi");
        }

        return result;
    }

    public void addTracksToSpotifyPlaylist(String playlistId, List<String> trackUris) {
        int batchSize = 100;
        for (int i = 0; i < trackUris.size(); i += batchSize) {
            List<String> batch = trackUris.subList(i, Math.min(i + batchSize, trackUris.size()));
            String[] uris = batch.toArray(new String[0]);
            AddItemsToPlaylistRequest request = spotifyApi.addItemsToPlaylist(playlistId, uris).build();
            try {
                request.execute();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (SpotifyWebApiException e) {
                throw new RuntimeException(e);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
