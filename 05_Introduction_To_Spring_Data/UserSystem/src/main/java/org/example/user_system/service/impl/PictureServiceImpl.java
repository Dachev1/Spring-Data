package org.example.user_system.service.impl;

import org.example.user_system.data.entities.Picture;
import org.example.user_system.data.repositories.PictureRepository;
import org.example.user_system.service.PictureService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PictureServiceImpl implements PictureService {
    private final PictureRepository pictureRepository;

    public PictureServiceImpl(PictureRepository pictureRepository) {
        this.pictureRepository = pictureRepository;
    }

    @Override
    public void savePicture(Picture picture) {
        this.pictureRepository.saveAndFlush(picture);
    }

    @Override
    public List<Picture> findAllPictures() {
        return this.pictureRepository.findAll();
    }
}