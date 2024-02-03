package com.mybackyard.backend.dto.service.implementation;

import com.mybackyard.backend.dto.mapper.PlantDtoMapper;
import com.mybackyard.backend.dto.model.PlantDto;
import com.mybackyard.backend.dto.service.interfaces.PlantDtoService;
import com.mybackyard.backend.model.Plant;
import com.mybackyard.backend.model.Yard;
import com.mybackyard.backend.validation.interfaces.DtoValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PlantDtoServiceImplTest {

    private PlantDtoMapper plantDtoMapper;
    private DtoValidator dtoValidator;
    private PlantDtoService plantDtoService;

    @BeforeEach
    void setUp() {
        plantDtoMapper = mock(PlantDtoMapper.class);
        dtoValidator = mock(DtoValidator.class);
        plantDtoService = new PlantDtoServiceImpl(plantDtoMapper,dtoValidator);
    }

    @Test
    void processOutgoingPlantList() {
    }

    @Test
    void processOutgoingPlant() {
    }

    @Test
    void processIncomingPlantDto() {
        PlantDto plantDto = new PlantDto(1,"name", null, null, null, null, null, null, new ArrayList<>(), new ArrayList<>(), 1);
        PlantDto badPlantDto = new PlantDto(1,"name", null, "NOT_A_VALID_PLANT_TYPE", null, null, null, null, new ArrayList<>(), new ArrayList<>(), 1);
        Plant plant = new Plant();
        plant.setPlantId(1);
        plant.setName("name");
        plant.setYard(new Yard());

        when(dtoValidator.isWellFormed(plantDto)).thenReturn(true);
        when(dtoValidator.isWellFormed(badPlantDto)).thenReturn(false);
        when(plantDtoMapper.dtoToPlant(plantDto, false)).thenReturn(plant);

        assertEquals(plant, plantDtoService.processIncomingPlantDto(plantDto, false));
        assertThrows(RuntimeException.class, () -> plantDtoService.processIncomingPlantDto(badPlantDto, false));
    }

    @Test
    void addId() {
        PlantDto plantDto = new PlantDto(1,"name", null, null, null, null, null, null, new ArrayList<>(), new ArrayList<>(), 1);
        PlantDto basePlantDto = new PlantDto(0,"name", null, null, null, null, null, null, new ArrayList<>(), new ArrayList<>(), 1);
        long plantId = 1;
        assertEquals(plantDto, plantDtoService.addId(basePlantDto, plantId));
    }
}