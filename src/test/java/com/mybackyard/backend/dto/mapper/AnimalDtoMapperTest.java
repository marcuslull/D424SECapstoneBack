package com.mybackyard.backend.dto.mapper;

import com.mybackyard.backend.dto.model.AnimalDto;
import com.mybackyard.backend.model.Animal;
import com.mybackyard.backend.model.Yard;
import com.mybackyard.backend.model.enums.AnimalSubType;
import com.mybackyard.backend.model.enums.DietType;
import com.mybackyard.backend.model.enums.NativeAreaType;
import com.mybackyard.backend.service.interfaces.YardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AnimalDtoMapperTest {

    private AnimalDtoMapper animalDtoMapper;
    private List<Animal> animalList;
    private List<AnimalDto> animalDtoList;
    private Yard yard1;
    private Animal animal1;
    private Animal animal2;
    private AnimalDto animalDto1;
    private AnimalDto animalDto2;

    @BeforeEach
    void setUp() {
        YardService mockedYardService = mock(YardService.class); //dependency
        animalDtoMapper = new AnimalDtoMapper(mockedYardService);
        yard1 = new Yard();
        yard1.setYardId(1L);
        animal1 = new Animal();
        animal1.setAnimalId(1L);
        animal1.setName("animal1");
        animal1.setNotes(new ArrayList<>());
        animal1.setImages(new ArrayList<>());
        animal1.setYard(yard1);
        animal2 = new Animal();
        animal2.setAnimalId(2L);
        animal2.setName("animal2");
        animal2.setNotes(new ArrayList<>());
        animal2.setImages(new ArrayList<>());
        animal2.setYard(yard1);
        animalList = List.of(animal1, animal2);

        animalDto1 = new AnimalDto(1L, "animal1", "", "", "", new ArrayList<>(), new ArrayList<>(), 1L);
        animalDto2 = new AnimalDto(2L, "animal2", "", "", "", new ArrayList<>(), new ArrayList<>(), 1L);
        animalDtoList = List.of(animalDto1, animalDto2);

        when(mockedYardService.getYardById(1L)).thenReturn(Optional.ofNullable(yard1));
    }

    @Test
    void animalsToDtos() {
        assertEquals(animalDtoList, animalDtoMapper.animalsToDtos(animalList));
    }

    @Test
    void animalToDto() {
        assertEquals(animalDto1, animalDtoMapper.animalToDto(animal1));
    }

    @Test
    void dtoToAnimal() {
        AnimalDto animalDto3 = new AnimalDto(0, "animal3", "FISH", "PARASITE", "FOREST", null, null, 1L);
        Animal animal3 = new Animal();
        animal3.setName("animal3");
        animal3.setAnimalSubType(AnimalSubType.FISH);
        animal3.setDietType(DietType.PARASITE);
        animal3.setNativeAreaType(NativeAreaType.FOREST);
        animal3.setYard(yard1);

        assertEquals(animal3.getName(), animalDtoMapper.dtoToAnimal(animalDto3, true).getName());
        assertEquals(animal3.getName(), animalDtoMapper.dtoToAnimal(animalDto3, false).getName());
        assertEquals(animal3.getAnimalSubType(), animalDtoMapper.dtoToAnimal(animalDto3, false).getAnimalSubType());
        assertEquals(animal3.getYard(), animalDtoMapper.dtoToAnimal(animalDto3, false).getYard());
    }
}