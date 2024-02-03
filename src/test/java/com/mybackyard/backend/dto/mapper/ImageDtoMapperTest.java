package com.mybackyard.backend.dto.mapper;

import com.mybackyard.backend.dto.model.ImageDto;
import com.mybackyard.backend.model.Image;
import com.mybackyard.backend.service.interfaces.AnimalService;
import com.mybackyard.backend.service.interfaces.PlantService;
import com.mybackyard.backend.service.interfaces.YardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class ImageDtoMapperTest {

    private YardService yardService;
    private PlantService plantService;
    private AnimalService animalService;
    private ImageDtoMapper imageDtoMapper;

    @BeforeEach
    void setUp() {
        yardService = mock(YardService.class);
        plantService = mock(PlantService.class);
        animalService = mock(AnimalService.class);
        imageDtoMapper = new ImageDtoMapper(yardService, plantService, animalService);
    }

    @Test
    void imagesToDtos() {
        ImageDto imageDto = new ImageDto(1, "location", 0, 0, 0);
        List<ImageDto> imageDtoList = List.of(imageDto);
        Image image = new Image();
        image.setImageId(1);
        image.setLocation("location");
        List<Image> imageList = List.of(image);

        assertEquals(imageDtoList, imageDtoMapper.imagesToDtos(imageList));
    }

    @Test
    void imageToDto() {
        ImageDto imageDto1 = new ImageDto(1, "location", 0, 0, 0);
        Image image1 = new Image();
        image1.setImageId(1);
        image1.setLocation("location");

        assertEquals(imageDto1, imageDtoMapper.imageToDto(image1));
    }

    @Test
    void dtoToImage() {
        Image image1 = new Image();
        image1.setImageId(1);
        image1.setLocation("location");
        ImageDto imageDto3 = new ImageDto(1, "location", 0, 0, 0);


        assertEquals(image1.getLocation(), imageDtoMapper.dtoToImage(imageDto3, true).getLocation());
        assertEquals(image1.getLocation(), imageDtoMapper.dtoToImage(imageDto3, false).getLocation());
    }
}