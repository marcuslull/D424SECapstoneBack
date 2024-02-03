package com.mybackyard.backend.dto.service.implementation;

import com.mybackyard.backend.dto.mapper.AnimalDtoMapper;
import com.mybackyard.backend.dto.model.AnimalDto;
import com.mybackyard.backend.dto.service.interfaces.AnimalDtoService;
import com.mybackyard.backend.model.Animal;
import com.mybackyard.backend.validation.interfaces.DtoValidator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimalDtoServiceImpl implements AnimalDtoService {

    private final AnimalDtoMapper animalDtoMapper;
    private final DtoValidator dtoValidator;

    public AnimalDtoServiceImpl(AnimalDtoMapper animalDtoMapper, DtoValidator dtoValidator) {
        this.animalDtoMapper = animalDtoMapper;
        this.dtoValidator = dtoValidator;
    }

    @Override
    public List<AnimalDto> processOutgoingAnimalList(List<Animal> animalList) {
        return animalDtoMapper.animalsToDtos(animalList);
    }

    @Override
    public AnimalDto processOutgoingAnimal(Animal animal) {
        return animalDtoMapper.animalToDto(animal);
    }

    @Override
    public Animal processIncomingAnimalDto(AnimalDto animalDto, Boolean isFromPatch) {
        if (!dtoValidator.isWellFormed(animalDto)) {
            throw new RuntimeException("Malformed DTO");
        }
        return animalDtoMapper.dtoToAnimal(animalDto, isFromPatch);
    }

    @Override
    public AnimalDto addId(AnimalDto animalDto, long animalId) {
        return new AnimalDto(animalId, animalDto.name(), animalDto.subType(), animalDto.dietType(),
                animalDto.nativeAreaType(), animalDto.noteIds(), animalDto.imageIds(), animalDto.yardId());
    }
}
