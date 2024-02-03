package com.mybackyard.backend.repository;

import com.mybackyard.backend.model.Plant;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PlantRepository  extends CrudRepository<Plant, Long> {
    List<Plant> findPlantsByUserId(long userId);
    Optional<Plant> findPlantByPlantIdAndUserId(long plantId, long userId);
    void deletePlantByPlantIdAndUserId(long plantId, long userId);
    List<Plant> findPlantsByNameContainingAndUserId(String query, long principal);
}
