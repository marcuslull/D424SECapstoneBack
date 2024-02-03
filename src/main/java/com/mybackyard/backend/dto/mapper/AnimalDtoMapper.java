package com.mybackyard.backend.dto.mapper;

import com.mybackyard.backend.dto.model.AnimalDto;
import com.mybackyard.backend.model.Animal;
import com.mybackyard.backend.model.enums.AnimalSubType;
import com.mybackyard.backend.model.enums.DietType;
import com.mybackyard.backend.model.enums.NativeAreaType;
import com.mybackyard.backend.service.interfaces.YardService;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Component
public class AnimalDtoMapper {

    private final YardService yardService;

    public AnimalDtoMapper(YardService yardService) {
        this.yardService = yardService;
    }

    public List<AnimalDto> animalsToDtos(List<Animal> animalList) {

        List<AnimalDto> animalDtos = new ArrayList<>();
        for (Animal animal : animalList) {
            animalDtos.add(convertAnimalToDto(animal));
        }
        return animalDtos;
    }

    public AnimalDto animalToDto(Animal animal) {
        return convertAnimalToDto(animal);
    }

    public Animal dtoToAnimal(AnimalDto animalDto, Boolean isFromPatch) {
        return convertDtoToAnimal(animalDto, isFromPatch);
    }

    private Animal convertDtoToAnimal(AnimalDto animalDto, Boolean isFromPatch) {
        // this could be from a POST or PATCH request so, we need to maintain the empty fields while converting

        Animal animal = new Animal();

        if (isFromPatch) {
            for (Field field : animalDto.getClass().getDeclaredFields()) {
                field.setAccessible(true); // not sure if this is needed
                try {
                    switch (field.getName()) {
                        case "name" -> {
                            if (field.get(animalDto) != null) animal.setName((String) field.get(animalDto));
                        }
                        case "subType" -> {
                            if (field.get(animalDto) != null)
                                animal.setAnimalSubType(AnimalSubType.valueOf((String) field.get(animalDto)));
                        }
                        case "dietType" -> {
                            if (field.get(animalDto) != null)
                                animal.setDietType(DietType.valueOf((String) field.get(animalDto)));
                        }
                        case "nativeAreaType" -> {
                            if (field.get(animalDto) != null)
                                animal.setNativeAreaType(NativeAreaType.valueOf((String) field.get(animalDto)));
                        }
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            return animal;
        }

        animal.setName(animalDto.name());
        animal.setAnimalSubType(AnimalSubType.valueOf(animalDto.subType()));
        animal.setDietType(DietType.valueOf(animalDto.dietType()));
        animal.setNativeAreaType(NativeAreaType.valueOf(animalDto.nativeAreaType()));
        animal.setNotes(new ArrayList<>());
        animal.setImages(new ArrayList<>());
        animal.setYard(yardService.getYardById(animalDto.yardId())
                .orElseThrow(() -> new NoSuchElementException("Yard id associated with animal does not exist")));

        return animal;
    }

    private AnimalDto convertAnimalToDto(Animal animal) {

        ArrayList<Long> noteIds = new ArrayList<>();
        animal.getNotes().forEach(note -> noteIds.add(note.getNoteId()));

        ArrayList<Long> imageIds = new ArrayList<>();
        animal.getImages().forEach(image -> imageIds.add(image.getImageId()));

        return new AnimalDto(
                animal.getAnimalId(),
                animal.getName(),
                Optional.ofNullable(animal.getAnimalSubType()).map(Enum::toString).orElse(""),
                Optional.ofNullable(animal.getDietType()).map(Enum::toString).orElse(""),
                Optional.ofNullable(animal.getNativeAreaType()).map(Enum::toString).orElse(""),
                noteIds,
                imageIds,
                animal.getYard().getYardId()
        );
    }
}
