package com.mybackyard.backend.validation.implementation;

import com.mybackyard.backend.dto.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class DtoValidatorImplTest {

    DtoValidatorImpl dtoValidator;
    AnimalDto animalDto1, animalDto2;
    ImageDto imageDto1, imageDto2;
    NoteDto noteDto1, noteDto2;
    PlantDto plantDto1, plantDto2;
    UserDto userDto1, userDto2;
    YardDto yardDto1, yardDto2;

    @BeforeEach
    void setUp() {
        dtoValidator = new DtoValidatorImpl();

        animalDto1 = new AnimalDto(1, "name", "BIRD", "HERBIVORE", "GRASSLAND",
                null, null, 1);
        animalDto2 = new AnimalDto(2, "name", "AMPHIBIAN", "This is bad",
                "TUNDRA", null, null, 1);
        imageDto1 = new ImageDto(1, "location", 1, 1, 1);
        imageDto2 = new ImageDto(-1, "location", 1, 1, 1);
        noteDto1 = new NoteDto(1, "comment", 1, 1, 1);
        noteDto2 = new NoteDto(2, "comment", 1, -1, 1);
        plantDto1 = new PlantDto(1, "name", "ZONE_6", "DESERT", "SHRUB",
                "SILTY", "PARTIAL_SUN", "EVERY_OTHER_DAY", new ArrayList<Long>(),
                new ArrayList<Long>(), 1);
        plantDto2 = new PlantDto(2, "name", "ZONE_8", "WETLAND",
                "This is wrong", "LOAM", "FULL_SHADE", "EVERY_OTHER_WEEK",
                new ArrayList<Long>(), new ArrayList<Long>(), 1);
        userDto1 = new UserDto(1, "first", "last", "test@test.com", "password",
                new ArrayList<Long>());
        userDto2 = new UserDto(2, "first", "last", "this should fail", "password",
                new ArrayList<Long>());
        yardDto1 = new YardDto(1, "name", "ZONE_12", "CLAY", "PARTIAL_SHADE",
                "FRONT_YARD",  new ArrayList<Long>(),  new ArrayList<Long>(),  new ArrayList<Long>(), 1);
        yardDto2 = new YardDto(2, "name", "ZONE_10", "CHALKY", "FULL_SUN",
                "This should fail",  new ArrayList<Long>(),  new ArrayList<Long>(),  new ArrayList<Long>(),
                1);
    }

    @Test
    void isWellFormed() {
        assertTrue(dtoValidator.isWellFormed(animalDto1));
        assertFalse(dtoValidator.isWellFormed(animalDto2));
        assertTrue(dtoValidator.isWellFormed(imageDto1));
        assertFalse(dtoValidator.isWellFormed(imageDto2));
        assertTrue(dtoValidator.isWellFormed(noteDto1));
        assertFalse(dtoValidator.isWellFormed(noteDto2));
        assertTrue(dtoValidator.isWellFormed(plantDto1));
        assertFalse(dtoValidator.isWellFormed(plantDto2));
        assertTrue(dtoValidator.isWellFormed(userDto1));
        assertFalse(dtoValidator.isWellFormed(userDto2));
        assertTrue(dtoValidator.isWellFormed(yardDto1));
        assertFalse(dtoValidator.isWellFormed(yardDto2));
    }
}