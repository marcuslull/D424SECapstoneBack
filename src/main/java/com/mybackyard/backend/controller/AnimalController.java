package com.mybackyard.backend.controller;

import com.mybackyard.backend.dto.model.AnimalDto;
import com.mybackyard.backend.dto.service.interfaces.AnimalDtoService;
import com.mybackyard.backend.model.Animal;
import com.mybackyard.backend.service.interfaces.AnimalService;
import com.mybackyard.backend.validation.interfaces.SearchValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AnimalController {

    private final AnimalService animalService;
    private final AnimalDtoService animalDtoService;
    private final SearchValidator searchValidator;

    public AnimalController(AnimalService animalService, AnimalDtoService animalDtoService, SearchValidator searchValidator) {
        this.animalService = animalService;
        this.animalDtoService = animalDtoService;
        this.searchValidator = searchValidator;
    }

    @GetMapping("/animal/search")
    public List<AnimalDto> getAnimalsSearch(@RequestParam(required = false) String name) {
        if (searchValidator.isValidNameSearch(name)) {
            // TODO: Handle the query validation and case sensitivity and illegal characters- v.N
            List<Animal> animalList = animalService.getAllAnimalsSearch(name);
            return animalDtoService.processOutgoingAnimalList(animalList);
        }
        return Collections.emptyList();
    }

    @GetMapping("/animal")
    public List<AnimalDto> getAnimals() {
        List<Animal> animalList = animalService.getAllAnimals();
        return animalDtoService.processOutgoingAnimalList(animalList);
    }

    @DeleteMapping("/animal")
    public ResponseEntity<String> deleteAnimals() {
        // TODO: do something with this - v.N
        return ResponseEntity.ok("Doesn't do anything yet - May not need this");
    }

    @GetMapping("/animal/{id}")
    public AnimalDto getAnimal(@PathVariable String id) {
         Optional<Animal> optionalAnimal = animalService.getAnimalById(Long.parseLong(id));
         if (optionalAnimal.isPresent()) {
             return animalDtoService.processOutgoingAnimal(optionalAnimal.get());
         }
         else { throw new NoSuchElementException("No such element found"); }
    }

    @PostMapping("/animal")
    public ResponseEntity<AnimalDto> postAnimal(@RequestBody AnimalDto animalDto) {
        Animal animal = animalDtoService.processIncomingAnimalDto(animalDto, false);
        long animalId = animalService.saveAnimal(animal);
        AnimalDto recreatedAnimalDto = animalDtoService.addId(animalDto, animalId);
        return ResponseEntity.created(URI.create("/api/animal/" + animalId)).body(recreatedAnimalDto);
    }

    @PatchMapping("/animal/{id}")
    public ResponseEntity<AnimalDto> patchAnimal(@PathVariable String id, @RequestBody AnimalDto animalDto) {
        Animal animal = animalDtoService.processIncomingAnimalDto(animalDto, true);
        Animal returnedAnimal = animalService.updateAnimal(id, animal);
        AnimalDto returnedAnimalDto = animalDtoService.processOutgoingAnimal(returnedAnimal);
        return ResponseEntity.ok(returnedAnimalDto);
    }

    @DeleteMapping("/animal/{id}")
    public ResponseEntity<String> deleteAnimal(@PathVariable String id) {
        animalService.deleteAnimalById(Long.parseLong(id));
        return ResponseEntity.noContent().build();
    }
}
