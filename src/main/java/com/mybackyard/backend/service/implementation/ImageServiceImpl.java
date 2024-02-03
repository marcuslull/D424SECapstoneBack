package com.mybackyard.backend.service.implementation;

import com.mybackyard.backend.model.Image;
import com.mybackyard.backend.repository.ImageRepository;
import com.mybackyard.backend.service.interfaces.ApiKeyService;
import com.mybackyard.backend.service.interfaces.CompareAndUpdate;
import com.mybackyard.backend.service.interfaces.ImageService;
import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final CompareAndUpdate compareAndUpdate;
    private final ApiKeyService apiKeyService;

    public ImageServiceImpl(ImageRepository imageRepository, CompareAndUpdate compareAndUpdate, ApiKeyService apiKeyService) {
        this.imageRepository = imageRepository;
        this.compareAndUpdate = compareAndUpdate;
        this.apiKeyService = apiKeyService;
    }

    @Override
    public List<Image> getAllImages() {
        long principalId = apiKeyService.matchKeyToUserId(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        return imageRepository.findImagesByUserId(principalId);
    }

    @Override
    public Optional<Image> getImageById(Long imageId) {
        long principalId = apiKeyService.matchKeyToUserId(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        return imageRepository.findImageByImageIdAndUserId(imageId, principalId);
    }

    @Override
    public long saveImage(Image image) {
        long principalId = apiKeyService.matchKeyToUserId(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        image.setUserId(principalId);
        return imageRepository.save(image).getImageId();
    }

    @Override
    @Transactional
    public void deleteImageById(long id) {
        long principalId = apiKeyService.matchKeyToUserId(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        imageRepository.deleteImageByImageIdAndUserId(id, principalId);
    }

    @Override
    public Image updateImage(String id, Image image) {
        long principalId = apiKeyService.matchKeyToUserId(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        image.setImageId(Long.parseLong(id));
        Image baseImage = imageRepository.findImageByImageIdAndUserId(Long.valueOf(id), principalId)
                .orElseThrow(() -> new NoSuchElementException("Image not found with id: " + id));
        try {
            return imageRepository.save(compareAndUpdate.updateImage(baseImage, image));
        }
        catch (Exception e) {
            // TODO: figure out what to do with these exceptions - v.N
        }
        return baseImage;
    }
}
