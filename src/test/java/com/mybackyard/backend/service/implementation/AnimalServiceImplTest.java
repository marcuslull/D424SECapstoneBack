package com.mybackyard.backend.service.implementation;

import com.mybackyard.backend.model.Animal;
import com.mybackyard.backend.repository.AnimalRepository;
import com.mybackyard.backend.service.interfaces.AnimalService;
import com.mybackyard.backend.service.interfaces.ApiKeyService;
import com.mybackyard.backend.service.interfaces.CompareAndUpdate;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class AnimalServiceImplTest {

    private AnimalRepository animalRepository;
    private CompareAndUpdate compareAndUpdate;
    private static ApiKeyService apikeyService;
    private AnimalService animalService;
    private MockedStatic<SecurityContextHolder> securityContextHolder;
    private SecurityContext securityContext;
    private Authentication authentication;
    private Principal principal;


    @BeforeEach
    void setUp() {
        animalRepository = mock(AnimalRepository.class);
        compareAndUpdate = mock(CompareAndUpdate.class);
        apikeyService = mock(ApiKeyService.class);
        animalService = new AnimalServiceImpl(animalRepository, compareAndUpdate, apikeyService);

        // below 9 lines are to mock the principalId
        securityContextHolder = mockStatic(SecurityContextHolder.class);
        securityContext = mock(SecurityContext.class);
        authentication = mock(Authentication.class);
        principal = mock(Principal.class);
        securityContextHolder.when(() -> SecurityContextHolder.getContext()).thenReturn(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(principal);
        when(principal.toString()).thenReturn("1");
        when(apikeyService.matchKeyToUserId(anyString())).thenReturn(1L);
    }

    // need this in order to create a new security context for each test
    @AfterEach
    void tearDown() {
        securityContextHolder.close();
    }

    @Test
    void getAllAnimals() {
        Animal animal = new Animal();
        List<Animal> listOfAnimals = new ArrayList<>();
        listOfAnimals.add(animal);
        when(animalRepository.findAnimalsByUserId(1L)).thenReturn(listOfAnimals);

        assertEquals(listOfAnimals, animalService.getAllAnimals());
    }

    @Test
    void getAnimalById() {
        long animalId = 1L;
        long userId = 1L;
        Animal animal = new Animal();
        animal.setAnimalId(animalId);
        animal.setUserId(userId);
        Optional<Animal> optionalAnimal = Optional.of(animal);
        when(animalRepository.findAnimalByAnimalIdAndUserId(animalId, userId)).thenReturn(optionalAnimal);

        assertEquals(optionalAnimal, animalService.getAnimalById(animalId));
    }

    @Test
    void saveAnimal() {
        Animal animal = new Animal();
        long animalId = 1L;
        animal.setAnimalId(animalId);
        when(animalRepository.save(animal)).thenReturn(animal);

        assertEquals(animalId, animalService.saveAnimal(animal));
    }

    @Test
    void deleteAnimalById() {
    }

    @Test
    void updateAnimal() throws IllegalAccessException {
        long animalId = 1L;
        long userId = 1L;
        Animal animal = new Animal();
        animal.setAnimalId(animalId);
        animal.setUserId(userId);
        animal.setName("name");
        Optional<Animal> baseAnimal = Optional.of(animal);
        Animal patch = new Animal();
        patch.setName("New Name");
        Animal updatedAnimal = new Animal();
        updatedAnimal.setAnimalId(animalId);
        updatedAnimal.setUserId(userId);
        updatedAnimal.setName("New Name");
        when(animalRepository.findAnimalByAnimalIdAndUserId(animalId, userId)).thenReturn(baseAnimal);
        when(compareAndUpdate.updateAnimal(baseAnimal.get(), patch)).thenReturn(updatedAnimal);
        when(animalRepository.save(updatedAnimal)).thenReturn(updatedAnimal);

        assertEquals(updatedAnimal, animalService.updateAnimal(String.valueOf(animalId), patch));
    }

    @Test
    void getAllAnimalsSearch() {
        String search = "Ro";
        long animalId = 1L;
        long userId = 1L;
        Animal animal = new Animal();
        animal.setAnimalId(animalId);
        animal.setUserId(userId);
        animal.setName("Rover");
        List<Animal> searchedList = List.of(animal);
        when(animalRepository.findAnimalsByNameContainingAndUserId(search, userId)).thenReturn(searchedList);

        assertEquals(searchedList, animalService.getAllAnimalsSearch(search));
    }
}