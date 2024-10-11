package org.example.user_system.service;

import org.example.user_system.data.entities.Picture;

import java.util.List;

public interface PictureService {
    void savePicture(Picture picture);

    List<Picture> findAllPictures();
}
