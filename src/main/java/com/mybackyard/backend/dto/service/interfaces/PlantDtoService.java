package com.mybackyard.backend.dto.service.interfaces;

import com.mybackyard.backend.dto.model.PlantDto;
import com.mybackyard.backend.model.Plant;

import java.util.List;

public interface PlantDtoService {
    List<PlantDto> processOutgoingPlantList(List<Plant> plantList);

    PlantDto processOutgoingPlant(Plant plant);

    Plant processIncomingPlantDto(PlantDto plantDto, Boolean isFromPatch);

    PlantDto addId(PlantDto plantDto, long plantId);
}
