package com.mybackyard.backend.service.implementation;

import com.mybackyard.backend.model.Plant;
import com.mybackyard.backend.repository.PlantRepository;
import com.mybackyard.backend.service.interfaces.ApiKeyService;
import com.mybackyard.backend.service.interfaces.CompareAndUpdate;
import com.mybackyard.backend.service.interfaces.PlantService;
import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class PlantServiceImpl implements PlantService {

    private final PlantRepository plantRepository;
    private final CompareAndUpdate compareAndUpdate;
    private final ApiKeyService apiKeyService;


    public PlantServiceImpl(PlantRepository plantRepository, CompareAndUpdate compareAndUpdate, ApiKeyService apiKeyService) {
        this.plantRepository = plantRepository;
        this.compareAndUpdate = compareAndUpdate;
        this.apiKeyService = apiKeyService;
    }

    @Override
    public List<Plant> getAllPlants() {
        long principalId = apiKeyService.matchKeyToUserId(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        return plantRepository.findPlantsByUserId(principalId);
    }

    @Override
    public Optional<Plant> getPlantById(long id) {
        long principalId = apiKeyService.matchKeyToUserId(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        return plantRepository.findPlantByPlantIdAndUserId(id, principalId);
    }

    @Override
    public long savePlant(Plant plant) {
        long principalId = apiKeyService.matchKeyToUserId(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        plant.setUserId(principalId);
        return plantRepository.save(plant).getPlantId();
    }

    @Override
    @Transactional
    public void deletePlantById(long id) {
        long principalId = apiKeyService.matchKeyToUserId(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        plantRepository.deletePlantByPlantIdAndUserId(id, principalId);
    }

    @Override
    public Plant updatePlant(String id, Plant plant) {
        long principalId = apiKeyService.matchKeyToUserId(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        plant.setPlantId(Long.parseLong(id));
        Plant basePlant = plantRepository.findPlantByPlantIdAndUserId(Long.valueOf(id), principalId)
                .orElseThrow(() -> new NoSuchElementException("Plant not found with id: " + id));
        try {
            return plantRepository.save(compareAndUpdate.updatePlant(basePlant, plant));
        }
        catch (Exception e) {
            // TODO: figure out what to do with these exceptions - v.N
        }
        return basePlant;
    }

    @Override
    public List<Plant> getAllPlantsSearch(String query) {
        long principalId = apiKeyService.matchKeyToUserId(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        return plantRepository.findPlantsByNameContainingAndUserId(query, principalId);
    }
}
