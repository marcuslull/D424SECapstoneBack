package com.mybackyard.backend.service.interfaces;

import com.mybackyard.backend.model.Plant;

import java.util.List;
import java.util.Optional;

public interface PlantService {
    Optional<Plant> getPlantById(long id);
    List<Plant> getAllPlants();
    long savePlant(Plant plant);
    void deletePlantById(long id);
    Plant updatePlant(String id, Plant plant);
    List<Plant> getAllPlantsSearch(String query);
}
