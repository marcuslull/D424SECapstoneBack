package com.mybackyard.backend.dto.service.implementation;

import com.mybackyard.backend.dto.mapper.PlantDtoMapper;
import com.mybackyard.backend.dto.model.PlantDto;
import com.mybackyard.backend.dto.service.interfaces.PlantDtoService;
import com.mybackyard.backend.model.Plant;
import com.mybackyard.backend.validation.interfaces.DtoValidator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlantDtoServiceImpl implements PlantDtoService {

    private final PlantDtoMapper plantDtoMapper;
    private final DtoValidator dtoValidator;

    public PlantDtoServiceImpl(PlantDtoMapper plantDtoMapper, DtoValidator dtoValidator) {
        this.plantDtoMapper = plantDtoMapper;
        this.dtoValidator = dtoValidator;
    }

    @Override
    public List<PlantDto> processOutgoingPlantList(List<Plant> plantList) {
        return plantDtoMapper.plantsToDtos(plantList);
    }

    @Override
    public PlantDto processOutgoingPlant(Plant plant) {
        return plantDtoMapper.plantToDto(plant);
    }

    @Override
    public Plant processIncomingPlantDto(PlantDto plantDto, Boolean isFromPatch) {
        if (!dtoValidator.isWellFormed(plantDto)) {
            throw new RuntimeException("Malformed DTO");
        }
        return plantDtoMapper.dtoToPlant(plantDto, isFromPatch);
    }

    @Override
    public PlantDto addId(PlantDto plantDto, long plantId) {
        return new PlantDto(plantId, plantDto.name(), plantDto.hardinessZone(), plantDto.nativeAreaType(),
                plantDto.plantSubType(), plantDto.soilType(), plantDto.sunExposure(), plantDto.wateringFrequency(),
                plantDto.noteIds(), plantDto.imageIds(), plantDto.yardId());
    }
}
