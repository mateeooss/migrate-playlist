package com.mateeooss.migrate_playlist.services;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemListResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.google.api.services.youtube.YouTube;

import java.io.IOException;
import java.util.*;

@Service
public class YoutubeService {
    @Value("${youtube.apikey}")
    private String youtubeApiKey;

    public List<Map<String, String>> getYoutubePlaylist(String playlistId) throws IOException {
        List<Map<String, String>> tracks = new ArrayList<>();
        YouTube youtube = new YouTube.Builder(
            new NetHttpTransport(),
            JacksonFactory.getDefaultInstance(),
null
        ).setApplicationName("Migrator").build();

        YouTube.PlaylistItems.List request = youtube.playlistItems()
                .list(List.of("snippet"))
                .setPlaylistId(playlistId)
                .setKey(youtubeApiKey)
                .setMaxResults(500L);

        String nextPageToken = null;

        do {
            if(nextPageToken != null){
                request.setPageToken(nextPageToken);
            }

            PlaylistItemListResponse response = request.execute();

            for(PlaylistItem item : response.getItems()){
                Map<String, String> track = new HashMap<>();
                String videoId = item.getSnippet().getResourceId().getVideoId();

                track.put("title", item.getSnippet().getTitle());
                track.put("channel", item.getSnippet().getVideoOwnerChannelTitle());
                track.put("description", item.getSnippet().getDescription());
                track.put("channel_url", "https://www.youtube.com/channel/" + item.getSnippet().getVideoOwnerChannelId());
                track.put("youtube_url", "https://www.youtube.com/watch?v=" + videoId);
                tracks.add(track);
            }

            nextPageToken = response.getNextPageToken();
        } while (nextPageToken != null);

        return tracks;
    }
}
