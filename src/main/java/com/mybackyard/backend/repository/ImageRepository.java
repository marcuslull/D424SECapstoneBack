package com.mybackyard.backend.repository;

import com.mybackyard.backend.model.Image;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ImageRepository  extends CrudRepository<Image, Long> {
    List<Image> findImagesByUserId(long userId);
    Optional<Image> findImageByImageIdAndUserId(long imageId, long userId);
    void deleteImageByImageIdAndUserId(long noteId, long userId);
}
