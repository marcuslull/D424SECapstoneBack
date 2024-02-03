package com.mybackyard.backend.dto.mapper;

import com.mybackyard.backend.dto.model.PlantDto;
import com.mybackyard.backend.model.Plant;
import com.mybackyard.backend.model.enums.*;
import com.mybackyard.backend.service.interfaces.YardService;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Component
public class PlantDtoMapper {

    private final YardService yardService;

    public PlantDtoMapper(YardService yardService) {
        this.yardService = yardService;
    }

    public List<PlantDto> plantsToDtos(List<Plant> plantList) {

        List<PlantDto> plantDtos = new ArrayList<>();
        for (Plant plant : plantList) {
            plantDtos.add(convertPlantToDto(plant));
        }
        return plantDtos;
    }

    public PlantDto plantToDto(Plant plant) {
        return convertPlantToDto(plant);
    }

    public Plant dtoToPlant(PlantDto plantDto, Boolean isFromPatch) {
        return convertDtoToPlant(plantDto, isFromPatch);
    }

    private Plant convertDtoToPlant(PlantDto plantDto, Boolean isFromPatch) {
        // this could be from a POST or PATCH request so, we need to maintain the empty fields while converting

        Plant plant = new Plant();

        if (isFromPatch) {
            for (Field field : plantDto.getClass().getDeclaredFields()) {
                field.setAccessible(true); // not sure if this is needed
                try {
                    switch (field.getName()) {
                        case "name" -> {
                            if (field.get(plantDto) != null) plant.setName((String) field.get(plantDto));
                        }
                        case "hardinessZone" -> {
                            if (field.get(plantDto) != null)
                                plant.setHardinessZone(HardinessZone.valueOf((String) field.get(plantDto)));
                        }
                        case "nativeAreaType" -> {
                            if (field.get(plantDto) != null)
                                plant.setNativeAreaType((NativeAreaType.valueOf((String) field.get(plantDto))));
                        }
                        case "plantSubType" -> {
                            if (field.get(plantDto) != null)
                                plant.setPlantSubType((PlantSubType.valueOf((String) field.get(plantDto))));
                        }
                        case "soilType" -> {
                            if (field.get(plantDto) != null)
                                plant.setSoilType(SoilType.valueOf((String) field.get(plantDto)));
                        }
                        case "sunExposure" -> {
                            if (field.get(plantDto) != null)
                                plant.setSunExposure(SunExposure.valueOf((String) field.get(plantDto)));
                        }
                        case "wateringFrequency" -> {
                            if (field.get(plantDto) != null)
                                plant.setWateringFrequency(WateringFrequency.valueOf((String) field.get(plantDto)));
                        }
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            return plant;
        }

        plant.setName(plantDto.name());
        plant.setHardinessZone(HardinessZone.valueOf(plantDto.hardinessZone()));
        plant.setNativeAreaType(NativeAreaType.valueOf(plantDto.nativeAreaType()));
        plant.setPlantSubType(PlantSubType.valueOf(plantDto.plantSubType()));
        plant.setSoilType(SoilType.valueOf(plantDto.soilType()));
        plant.setSunExposure(SunExposure.valueOf(plantDto.sunExposure()));
        plant.setWateringFrequency(WateringFrequency.valueOf(plantDto.wateringFrequency()));
        plant.setYard(yardService.getYardById(plantDto.yardId())
                .orElseThrow(() -> new NoSuchElementException("Yard id associated with plant does not exist")));

        return plant;
    }

    private PlantDto convertPlantToDto(Plant plant) {

        ArrayList<Long> noteIds = new ArrayList<>();
        plant.getNotes().forEach(note -> noteIds.add(note.getNoteId()));

        ArrayList<Long> imageIds = new ArrayList<>();
        plant.getImages().forEach(image -> imageIds.add(image.getImageId()));

        return new PlantDto(
                plant.getPlantId(),
                plant.getName(),
                Optional.ofNullable(plant.getHardinessZone()).map(Enum::toString).orElse(""),
                Optional.ofNullable(plant.getNativeAreaType()).map(Enum::toString).orElse(""),
                Optional.ofNullable(plant.getPlantSubType()).map(Enum::toString).orElse(""),
                Optional.ofNullable(plant.getSoilType()).map(Enum::toString).orElse(""),
                Optional.ofNullable(plant.getSunExposure()).map(Enum::toString).orElse(""),
                Optional.ofNullable(plant.getWateringFrequency()).map(Enum::toString).orElse(""),
                noteIds,
                imageIds,
                plant.getYard().getYardId()
        );
    }
}
