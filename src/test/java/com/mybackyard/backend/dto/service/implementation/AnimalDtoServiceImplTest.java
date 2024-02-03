package com.mybackyard.backend.dto.service.implementation;

import com.mybackyard.backend.dto.mapper.AnimalDtoMapper;
import com.mybackyard.backend.dto.model.AnimalDto;
import com.mybackyard.backend.dto.service.interfaces.AnimalDtoService;
import com.mybackyard.backend.model.Animal;
import com.mybackyard.backend.model.Yard;
import com.mybackyard.backend.validation.interfaces.DtoValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AnimalDtoServiceImplTest {

    private AnimalDtoMapper animalDtoMapper;
    private DtoValidator dtoValidator;
    private AnimalDtoService animalDtoService;

    @BeforeEach
    void setUp() {
        animalDtoMapper = mock(AnimalDtoMapper.class);
        dtoValidator = mock(DtoValidator.class);
        animalDtoService = new AnimalDtoServiceImpl(animalDtoMapper,dtoValidator);
    }

    @Test
    void processOutgoingAnimalList() {
    }

    @Test
    void processOutgoingAnimal() {
    }

    @Test
    void processIncomingAnimalDto() {
        AnimalDto animalDto = new AnimalDto(1,"name", null, null, null, new ArrayList<>(), new ArrayList<>(), 1);
        AnimalDto badAnimalDto = new AnimalDto(1,"name", null, "NOT_A_VALID_DIET_TYPE", null, new ArrayList<>(), new ArrayList<>(), 1);
        Animal animal = new Animal();
        animal.setAnimalId(1);
        animal.setName("name");
        animal.setYard(new Yard());

        when(dtoValidator.isWellFormed(animalDto)).thenReturn(true);
        when(dtoValidator.isWellFormed(badAnimalDto)).thenReturn(false);
        when(animalDtoMapper.dtoToAnimal(animalDto, false)).thenReturn(animal);

        assertEquals(animal, animalDtoService.processIncomingAnimalDto(animalDto, false));
        assertThrows(RuntimeException.class, () -> animalDtoService.processIncomingAnimalDto(badAnimalDto, false));
    }

    @Test
    void addId() {
        AnimalDto animalDto = new AnimalDto(1,"name", null, null, null, new ArrayList<>(), new ArrayList<>(), 1);
        AnimalDto baseAnimalDto = new AnimalDto(0,"name", null, null, null, new ArrayList<>(), new ArrayList<>(), 1);
        long animalId = 1;
        assertEquals(animalDto, animalDtoService.addId(baseAnimalDto, animalId));
    }
}