package com.mybackyard.backend.service.implementation;

import com.mybackyard.backend.model.Image;
import com.mybackyard.backend.repository.ImageRepository;
import com.mybackyard.backend.service.interfaces.ApiKeyService;
import com.mybackyard.backend.service.interfaces.CompareAndUpdate;
import com.mybackyard.backend.service.interfaces.ImageService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ImageServiceImplTest {

    private ImageRepository imageRepository;
    private CompareAndUpdate compareAndUpdate;
    private ApiKeyService apikeyService;
    private ImageService imageService;
    private MockedStatic<SecurityContextHolder> securityContextHolder;
    private SecurityContext securityContext;
    private Authentication authentication;
    private Principal principal;

    @BeforeEach
    void setUp() {
        imageRepository = mock(ImageRepository.class);
        compareAndUpdate = mock(CompareAndUpdate.class);
        apikeyService = mock(ApiKeyService.class);
        imageService = new ImageServiceImpl(imageRepository, compareAndUpdate, apikeyService);

        // below 9 lines are to mock the principalId
        securityContextHolder = mockStatic(SecurityContextHolder.class);
        securityContext = mock(SecurityContext.class);
        authentication = mock(Authentication.class);
        principal = mock(Principal.class);
        securityContextHolder.when(() -> SecurityContextHolder.getContext()).thenReturn(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(principal);
        when(principal.toString()).thenReturn("1");
        when(apikeyService.matchKeyToUserId(anyString())).thenReturn(1L);
    }

    // need this in order to create a new security context for each test
    @AfterEach
    void tearDown() {
        securityContextHolder.close();
    }

    @Test
    void getAllImages() {
        long userId = 1L;
        long imageId = 1L;
        Image image = new Image();
        image.setImageId(imageId);
        image.setUserId(userId);
        List<Image> listOfImages = List.of(image);
        when(imageRepository.findImagesByUserId(userId)).thenReturn(listOfImages);

        assertEquals(listOfImages, imageService.getAllImages());
    }

    @Test
    void getImageById() {
        long userId = 1L;
        long imageId = 1L;
        Image image = new Image();
        image.setImageId(imageId);
        image.setUserId(userId);
        Optional<Image> optionalImage = Optional.of(image);
        when(imageRepository.findImageByImageIdAndUserId(imageId, userId)).thenReturn(optionalImage);

        assertEquals(optionalImage, imageService.getImageById(imageId));
    }

    @Test
    void saveImage() {
        Image image = new Image();
        long imageId = 1L;
        long userId = 1L;
        image.setImageId(imageId);
        image.setUserId(userId);
        when(imageRepository.save(image)).thenReturn(image);

        assertEquals(imageId, imageService.saveImage(image));
    }

    @Test
    void deleteImageById() {
    }

    @Test
    void updateImage() throws IllegalAccessException {
        long imageId = 1L;
        long userId = 1L;
        Image image = new Image();
        image.setImageId(imageId);
        image.setUserId(userId);
        image.setLocation("location");
        Optional<Image> baseImage = Optional.of(image);
        Image patch = new Image();
        patch.setLocation("new location");
        Image updatedImage = new Image();
        updatedImage.setImageId(imageId);
        updatedImage.setUserId(userId);
        updatedImage.setLocation("new location");
        when(imageRepository.findImageByImageIdAndUserId(imageId, userId)).thenReturn(baseImage);
        when(compareAndUpdate.updateImage(baseImage.get(), patch)).thenReturn(updatedImage);
        when(imageRepository.save(updatedImage)).thenReturn(updatedImage);

        assertEquals(updatedImage, imageService.updateImage(String.valueOf(imageId), patch));
    }
}