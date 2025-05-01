package com.mateeooss.migrate_playlist.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PlaylistService {

    @Autowired
    private YoutubeService youtubeService;
    @Autowired
    private SpotifyService spotifyService;

    public void migratePlaylist(){
        try {
            List<Map<String, String>> youtubeTracks = youtubeService.getYoutubePlaylist("PLmsuU-XichRFFGE9VUM-tPZA0_DhHnzEo");
            String spotifyPlayListId = spotifyService.createSpotifyPlaylist("");
            System.out.println(spotifyPlayListId);

            List<String> trackUris = new ArrayList<>();
            for (Map<String, String> track : youtubeTracks) {
                var trackUri = spotifyService.searchSpotifyTrack(track);
                if (trackUri != null) {
                    trackUris.add(trackUri);
                }
            }

            spotifyService.addTracksToSpotifyPlaylist(spotifyPlayListId, trackUris);

            System.out.println("Playlist migrada com sucesso!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
