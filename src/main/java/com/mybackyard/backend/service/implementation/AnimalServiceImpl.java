package com.mybackyard.backend.service.implementation;

import com.mybackyard.backend.model.Animal;
import com.mybackyard.backend.repository.AnimalRepository;
import com.mybackyard.backend.service.interfaces.AnimalService;
import com.mybackyard.backend.service.interfaces.ApiKeyService;
import com.mybackyard.backend.service.interfaces.CompareAndUpdate;
import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class AnimalServiceImpl implements AnimalService {

    private final AnimalRepository animalRepository;
    private final CompareAndUpdate compareAndUpdate;
    private final ApiKeyService apiKeyService;

    public AnimalServiceImpl(AnimalRepository animalRepository, CompareAndUpdate compareAndUpdate, ApiKeyService apiKeyService) {
        this.animalRepository = animalRepository;
        this.compareAndUpdate = compareAndUpdate;
        this.apiKeyService = apiKeyService;
    }

    @Override
    public List<Animal> getAllAnimals() {
        long principalId = apiKeyService.matchKeyToUserId(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        return animalRepository.findAnimalsByUserId(principalId);
    }

    @Override
    public Optional<Animal> getAnimalById(long id) {
        long principalId = apiKeyService.matchKeyToUserId(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        return animalRepository.findAnimalByAnimalIdAndUserId(id, principalId);
    }

    @Override
    public long saveAnimal(Animal animal) {
        long principalId = apiKeyService.matchKeyToUserId(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        animal.setUserId(principalId);
        return animalRepository.save(animal).getAnimalId();
    }

    @Override
    @Transactional
    public void deleteAnimalById(long id) {
        long principalId = apiKeyService.matchKeyToUserId(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        animalRepository.deleteAnimalByAnimalIdAndUserId(id, principalId);
    }

    @Override
    public Animal updateAnimal(String id, Animal animal) {
        long principalId = apiKeyService.matchKeyToUserId(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());

        animal.setAnimalId(Long.parseLong(id));
        Animal baseAnimal = animalRepository.findAnimalByAnimalIdAndUserId(Long.valueOf(id), principalId)
                .orElseThrow(() -> new NoSuchElementException("Animal not found with id: " + id));
        try {
            return animalRepository.save(compareAndUpdate.updateAnimal(baseAnimal, animal));
        }
        catch (Exception e) {
            // TODO: figure out what to do with these exceptions - v.N
        }
        return baseAnimal;
    }

    @Override
    public List<Animal> getAllAnimalsSearch(String query) {
        long principalId = apiKeyService.matchKeyToUserId(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        return animalRepository.findAnimalsByNameContainingAndUserId(query, principalId);
    }
}
