package com.mateeooss.migrate_playlist.controllers;

import com.mateeooss.migrate_playlist.services.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/playlist")
public class PlaylistController {

    @Autowired
    private PlaylistService playlistService;

    @PostMapping()
    public void migratePlaylist(){
        this.playlistService.migratePlaylist();;
    }
}
