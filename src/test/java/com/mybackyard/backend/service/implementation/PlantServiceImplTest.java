package com.mybackyard.backend.service.implementation;

import com.mybackyard.backend.model.Plant;
import com.mybackyard.backend.repository.PlantRepository;
import com.mybackyard.backend.service.interfaces.ApiKeyService;
import com.mybackyard.backend.service.interfaces.CompareAndUpdate;
import com.mybackyard.backend.service.interfaces.PlantService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlantServiceImplTest {

    private PlantRepository plantRepository;
    private CompareAndUpdate compareAndUpdate;
    private static ApiKeyService apikeyService;
    private PlantService plantService;
    private MockedStatic<SecurityContextHolder> securityContextHolder;
    private SecurityContext securityContext;
    private Authentication authentication;
    private Principal principal;

    @BeforeEach
    void setUp() {
        plantRepository = mock(PlantRepository.class);
        compareAndUpdate = mock(CompareAndUpdate.class);
        apikeyService = mock(ApiKeyService.class);
        plantService = new PlantServiceImpl(plantRepository, compareAndUpdate, apikeyService);

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
    void getAllPlants() {
        Plant plant = new Plant();
        List<Plant> listOfPlants = new ArrayList<>();
        listOfPlants.add(plant);
        when(plantRepository.findPlantsByUserId(1L)).thenReturn(listOfPlants);

        assertEquals(listOfPlants, plantService.getAllPlants());
    }

    @Test
    void getPlantById() {
        long plantId = 1L;
        long userId = 1L;
        Plant plant = new Plant();
        plant.setPlantId(plantId);
        plant.setUserId(userId);
        Optional<Plant> optionalPlant = Optional.of(plant);
        when(plantRepository.findPlantByPlantIdAndUserId(plantId, userId)).thenReturn(optionalPlant);

        assertEquals(optionalPlant, plantService.getPlantById(plantId));
    }

    @Test
    void savePlant() {
        Plant plant = new Plant();
        long plantId = 1L;
        plant.setPlantId(plantId);
        when(plantRepository.save(plant)).thenReturn(plant);

        assertEquals(plantId, plantService.savePlant(plant));
    }

    @Test
    void deletePlantById() {
    }

    @Test
    void updatePlant() throws IllegalAccessException {
        long plantId = 1L;
        long userId = 1L;
        Plant plant = new Plant();
        plant.setPlantId(plantId);
        plant.setUserId(userId);
        plant.setName("name");
        Optional<Plant> basePlant = Optional.of(plant);
        Plant patch = new Plant();
        patch.setName("New Name");
        Plant updatedPlant = new Plant();
        updatedPlant.setPlantId(plantId);
        updatedPlant.setUserId(userId);
        updatedPlant.setName("New Name");
        when(plantRepository.findPlantByPlantIdAndUserId(plantId, userId)).thenReturn(basePlant);
        when(compareAndUpdate.updatePlant(basePlant.get(), patch)).thenReturn(updatedPlant);
        when(plantRepository.save(updatedPlant)).thenReturn(updatedPlant);

        assertEquals(updatedPlant, plantService.updatePlant(String.valueOf(plantId), patch));
    }

    @Test
    void getAllPlantsSearch() {
        String search = "Ro";
        long plantId = 1L;
        long userId = 1L;
        Plant plant = new Plant();
        plant.setPlantId(plantId);
        plant.setUserId(userId);
        plant.setName("Rover");
        List<Plant> searchedList = List.of(plant);
        when(plantRepository.findPlantsByNameContainingAndUserId(search, userId)).thenReturn(searchedList);

        assertEquals(searchedList, plantService.getAllPlantsSearch(search));
    }
}