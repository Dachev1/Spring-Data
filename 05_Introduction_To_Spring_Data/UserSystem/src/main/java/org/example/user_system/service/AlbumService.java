package org.example.user_system.service;

import org.example.user_system.data.entities.Album;

import java.util.List;

public interface AlbumService {
    void saveAlbum(Album album);

    List<Album> findAllAlbums();
}
