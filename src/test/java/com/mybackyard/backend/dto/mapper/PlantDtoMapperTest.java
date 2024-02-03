package com.mybackyard.backend.dto.mapper;

import com.mybackyard.backend.dto.model.PlantDto;
import com.mybackyard.backend.model.Plant;
import com.mybackyard.backend.model.Yard;
import com.mybackyard.backend.service.interfaces.YardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.mybackyard.backend.model.enums.HardinessZone.ZONE_7;
import static com.mybackyard.backend.model.enums.NativeAreaType.GRASSLAND;
import static com.mybackyard.backend.model.enums.PlantSubType.SHRUB;
import static com.mybackyard.backend.model.enums.SoilType.CLAY;
import static com.mybackyard.backend.model.enums.SunExposure.PARTIAL_SUN;
import static com.mybackyard.backend.model.enums.WateringFrequency.EVERY_OTHER_DAY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PlantDtoMapperTest {

    private YardService yardService;
    private PlantDtoMapper plantDtoMapper;

    @BeforeEach
    void setUp() {
        yardService = mock(YardService.class);
        plantDtoMapper = new PlantDtoMapper(yardService);
    }

    @Test
    void plantsToDtos() {
        PlantDto plantDto = new PlantDto(1,"name",  "",  "",  "",  "",  "",  "", new ArrayList<>(), new ArrayList<>(), 0);
        List<PlantDto> plantDtoList = List.of(plantDto);
        Plant plant = new Plant();
        plant.setPlantId(1);
        plant.setName("name");
        Yard yard = new Yard();
        plant.setYard(yard);
        plant.setNotes(new ArrayList<>());
        plant.setImages(new ArrayList<>());
        List<Plant> plantList = List.of(plant);

        assertEquals(plantDtoList, plantDtoMapper.plantsToDtos(plantList));
    }

    @Test
    void plantToDto() {
        PlantDto plantDto = new PlantDto(1,"name",  "",  "",  "",  "",  "",  "", new ArrayList<>(), new ArrayList<>(), 0);
        Plant plant = new Plant();
        plant.setPlantId(1);
        plant.setName("name");
        Yard yard = new Yard();
        plant.setYard(yard);
        plant.setNotes(new ArrayList<>());
        plant.setImages(new ArrayList<>());

        assertEquals(plantDto, plantDtoMapper.plantToDto(plant));
    }

    @Test
    void dtoToPlant() {
        PlantDto plantDto = new PlantDto(1,"name",  "ZONE_7",  "GRASSLAND",  "SHRUB",  "CLAY",  "PARTIAL_SUN",  "EVERY_OTHER_DAY", new ArrayList<>(), new ArrayList<>(), 1);
        Plant plant = new Plant();
        plant.setPlantId(1);
        plant.setName("name");
        plant.setHardinessZone(ZONE_7);
        plant.setNativeAreaType(GRASSLAND);
        plant.setPlantSubType(SHRUB);
        plant.setSoilType(CLAY);
        plant.setSunExposure(PARTIAL_SUN);
        plant.setWateringFrequency(EVERY_OTHER_DAY);
        Yard yard = new Yard();
        yard.setYardId(1);
        plant.setYard(yard);
        plant.setNotes(new ArrayList<>());
        plant.setImages(new ArrayList<>());
        when(yardService.getYardById(1)).thenReturn(Optional.of(yard));

        assertEquals(plant.getName(), plantDtoMapper.dtoToPlant(plantDto, true).getName());
        assertEquals(plant.getName(), plantDtoMapper.dtoToPlant(plantDto, false).getName());
    }
}