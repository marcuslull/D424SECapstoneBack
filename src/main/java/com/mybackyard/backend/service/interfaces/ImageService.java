package com.mybackyard.backend.service.interfaces;

import com.mybackyard.backend.model.Image;

import java.util.List;
import java.util.Optional;

public interface ImageService {
    Optional<Image> getImageById(Long imageId);

    List<Image> getAllImages();

    long saveImage(Image image);

    Image updateImage(String id, Image image);

    void deleteImageById(long id);
}
