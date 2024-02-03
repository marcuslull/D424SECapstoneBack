package com.mybackyard.backend.service.interfaces;

import com.mybackyard.backend.model.Animal;

import java.util.List;
import java.util.Optional;

public interface AnimalService {

    List<Animal> getAllAnimals();

    Optional<Animal> getAnimalById(long id);

    long saveAnimal(Animal animal);

    void deleteAnimalById(long id);

    Animal updateAnimal(String id, Animal animal);

    List<Animal> getAllAnimalsSearch(String query);
}
