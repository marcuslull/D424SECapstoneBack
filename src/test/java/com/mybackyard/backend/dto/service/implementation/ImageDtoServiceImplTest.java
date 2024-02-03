package com.mybackyard.backend.dto.service.implementation;

import com.mybackyard.backend.dto.mapper.ImageDtoMapper;
import com.mybackyard.backend.dto.model.ImageDto;
import com.mybackyard.backend.dto.service.interfaces.ImageDtoService;
import com.mybackyard.backend.model.Image;
import com.mybackyard.backend.model.Yard;
import com.mybackyard.backend.validation.interfaces.DtoValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ImageDtoServiceImplTest {

    private ImageDtoMapper imageDtoMapper;
    private DtoValidator dtoValidator;
    private ImageDtoService imageDtoService;

    @BeforeEach
    void setUp() {
        imageDtoMapper = mock(ImageDtoMapper.class);
        dtoValidator = mock(DtoValidator.class);
        imageDtoService = new ImageDtoServiceImpl(imageDtoMapper,dtoValidator);
    }

    @Test
    void processOutgoingImageList() {
    }

    @Test
    void processOutgoingImage() {
    }

    @Test
    void processIncomingImageDto() {
        ImageDto imageDto = new ImageDto(1,"location", 1, 0, 0);
        ImageDto badImageDto = new ImageDto(1,"", 0, 0, 0);
        Image image = new Image();
        image.setImageId(1);
        image.setLocation("location");
        image.setYard(new Yard());

        when(dtoValidator.isWellFormed(imageDto)).thenReturn(true);
        when(dtoValidator.isWellFormed(badImageDto)).thenReturn(false);
        when(imageDtoMapper.dtoToImage(imageDto, false)).thenReturn(image);

        assertEquals(image, imageDtoService.processIncomingImageDto(imageDto, false));
        assertThrows(RuntimeException.class, () -> imageDtoService.processIncomingImageDto(badImageDto, false));
    }

    @Test
    void addId() {
        ImageDto imageDto = new ImageDto(1,"location", 1, 0, 0);
        ImageDto baseImageDto = new ImageDto(1,"location", 1, 0, 0);
        long imageId = 1;
        assertEquals(imageDto, imageDtoService.addId(baseImageDto, imageId));
    }
}