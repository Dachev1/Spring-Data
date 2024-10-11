package org.example.user_system.service.impl;

import org.example.user_system.data.entities.Album;
import org.example.user_system.data.repositories.AlbumRepository;
import org.example.user_system.service.AlbumService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlbumServiceImpl implements AlbumService {
    private final AlbumRepository albumRepository;

    public AlbumServiceImpl(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    @Override
    public void saveAlbum(Album album) {
        this.albumRepository.saveAndFlush(album);
    }

    @Override
    public List<Album> findAllAlbums() {
        return this.albumRepository.findAll();
    }
}