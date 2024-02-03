package com.mybackyard.backend.repository;

import com.mybackyard.backend.model.Animal;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AnimalRepository extends CrudRepository<Animal, Long> {

    List<Animal> findAnimalsByUserId(long userId);
    Optional<Animal> findAnimalByAnimalIdAndUserId(long animalId, long userId);
    void deleteAnimalByAnimalIdAndUserId(long noteId, long userId);
    List<Animal> findAnimalsByNameContainingAndUserId(String query, long principal);
}
