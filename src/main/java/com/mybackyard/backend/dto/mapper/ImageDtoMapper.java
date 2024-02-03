package com.mybackyard.backend.dto.mapper;

import com.mybackyard.backend.dto.model.ImageDto;
import com.mybackyard.backend.model.Image;
import com.mybackyard.backend.service.interfaces.AnimalService;
import com.mybackyard.backend.service.interfaces.PlantService;
import com.mybackyard.backend.service.interfaces.YardService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Component
public class ImageDtoMapper {

    private final YardService yardService;
    private final PlantService plantService;
    private final AnimalService animalService;

    public ImageDtoMapper(YardService yardService, PlantService plantService, AnimalService animalService) {
        this.yardService = yardService;
        this.plantService = plantService;
        this.animalService = animalService;
    }

    public List<ImageDto> imagesToDtos(List<Image> imageList) {
        List<ImageDto> imageDtos = new ArrayList<>();
        for (Image image : imageList) {
            imageDtos.add(convertImageToDto(image));
        }
        return imageDtos;
    }

    public ImageDto imageToDto(Image image) {
        return convertImageToDto(image);
    }

    public Image dtoToImage(ImageDto imageDto, Boolean isFromPatch) {
        return convertDtoToImage(imageDto, isFromPatch);
    }

    private Image convertDtoToImage(ImageDto imageDto, Boolean isFromPatch) {
        Image image = new Image();

        if (isFromPatch) {
            if (imageDto.location() != null) image.setLocation(imageDto.location());
            return image;
        }

        image.setLocation(imageDto.location());
        if (imageDto.yardId() != 0) image.setYard(yardService.getYardById(imageDto.yardId())
                .orElseThrow(() -> new NoSuchElementException("Yard id associated with image does not exist")));
        if (imageDto.plantId() != 0) image.setPlant(plantService.getPlantById(imageDto.plantId())
                .orElseThrow(() -> new NoSuchElementException("Plant id associated with image does not exist")));
        if (imageDto.animalId() != 0) image.setAnimal(animalService.getAnimalById(imageDto.animalId())
                .orElseThrow(() -> new NoSuchElementException("Animal id associated with image does not exist")));
        return image;
    }

    private ImageDto convertImageToDto(Image image) {
        long yardId = (image.getYard() == null ? 0 : image.getYard().getYardId());
        long plantId = (image.getPlant() == null ? 0 : image.getPlant().getPlantId());
        long animalId = (image.getAnimal() == null ? 0 : image.getAnimal().getAnimalId());

        return new ImageDto(image.getImageId(), image.getLocation(), yardId, plantId, animalId);
    }
}
