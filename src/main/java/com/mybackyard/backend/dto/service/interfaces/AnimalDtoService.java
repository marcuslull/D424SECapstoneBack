package com.mybackyard.backend.dto.service.interfaces;

import com.mybackyard.backend.dto.model.AnimalDto;
import com.mybackyard.backend.model.Animal;

import java.util.List;

public interface AnimalDtoService {
    List<AnimalDto> processOutgoingAnimalList(List<Animal> animalList);

    AnimalDto processOutgoingAnimal(Animal animal);

    Animal processIncomingAnimalDto(AnimalDto animalDto, Boolean isFromPatch);

    AnimalDto addId(AnimalDto animalDto, long animalId);
}
