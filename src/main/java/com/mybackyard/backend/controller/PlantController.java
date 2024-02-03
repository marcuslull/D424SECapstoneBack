package com.mybackyard.backend.controller;

import com.mybackyard.backend.dto.model.PlantDto;
import com.mybackyard.backend.dto.service.interfaces.PlantDtoService;
import com.mybackyard.backend.model.Plant;
import com.mybackyard.backend.service.interfaces.PlantService;
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
public class PlantController {

    private final PlantService plantService;
    private final PlantDtoService plantDtoService;
    private final SearchValidator searchValidator;

    public PlantController(PlantService plantService, PlantDtoService plantDtoService, SearchValidator searchValidator) {
        this.plantService = plantService;
        this.plantDtoService = plantDtoService;
        this.searchValidator = searchValidator;
    }

    @GetMapping("/plant/search")
    public List<PlantDto> getPlantSearch(@RequestParam(required = false) String name) {
        if (searchValidator.isValidNameSearch(name)) {
            // TODO: Handle the query validation and case sensitivity and illegal characters- v.N
            List<Plant> plantList = plantService.getAllPlantsSearch(name);
            return plantDtoService.processOutgoingPlantList(plantList);
        }
        return Collections.emptyList();
    }

    @GetMapping("/plant")
    public List<PlantDto> getPlants() {
        List<Plant> plantList = plantService.getAllPlants();
        return plantDtoService.processOutgoingPlantList(plantList);
    }

    @DeleteMapping("/plant")
    public ResponseEntity<String> deletePlants() {
        // TODO: do something with this - v.N
        return ResponseEntity.ok("Doesn't do anything yet - May not need this");
    }

    @GetMapping("/plant/{id}")
    public PlantDto getPlant(@PathVariable String id) {
        Optional<Plant> optionalPlant = plantService.getPlantById(Long.parseLong(id));
        if (optionalPlant.isPresent()) {
            return plantDtoService.processOutgoingPlant(optionalPlant.get());
        }
        else { throw new NoSuchElementException("No such element found"); }
    }

    @PostMapping("/plant")
    public ResponseEntity<PlantDto> postPlant(@RequestBody PlantDto plantDto) {
        Plant plant = plantDtoService.processIncomingPlantDto(plantDto, false);
        long plantId = plantService.savePlant(plant);
        PlantDto recreatedPlantDto = plantDtoService.addId(plantDto, plantId);
        return ResponseEntity.created(URI.create("/api/plant/" + plantId)).body(recreatedPlantDto);
    }

    @PatchMapping("/plant/{id}")
    public ResponseEntity<PlantDto> patchPlant(@PathVariable String id, @RequestBody PlantDto plantDto) {
        Plant plant = plantDtoService.processIncomingPlantDto(plantDto, true);
        Plant returnedPlant = plantService.updatePlant(id, plant);
        PlantDto returnedPlantDto = plantDtoService.processOutgoingPlant(returnedPlant);
        return ResponseEntity.ok(returnedPlantDto);
    }

    @DeleteMapping("/plant/{id}")
    public ResponseEntity<String> deletePlant(@PathVariable String id) {
        plantService.deletePlantById(Long.parseLong(id));
        return ResponseEntity.noContent().build();
    }
}
