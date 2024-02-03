package com.mybackyard.backend.controller;

import com.mybackyard.backend.dto.model.YardDto;
import com.mybackyard.backend.dto.service.interfaces.YardDtoService;
import com.mybackyard.backend.model.Yard;
import com.mybackyard.backend.service.interfaces.YardService;
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
public class YardController {

    private final YardService yardService;
    private final YardDtoService yardDtoService;
    private final SearchValidator searchValidator;

    public YardController(YardService yardService, YardDtoService yardDtoService, SearchValidator searchValidator) {
        this.yardService = yardService;
        this.yardDtoService = yardDtoService;
        this.searchValidator = searchValidator;
    }

    @GetMapping("/yard/search")
    public List<YardDto> getYardsSearch(@RequestParam(required = false) String name) {
        if (searchValidator.isValidNameSearch(name)) {
            // TODO: Handle the query validation and case sensitivity and illegal characters- v.N
            List<Yard> yardList = yardService.getAllYardsSearch(name);
            return yardDtoService.processOutgoingYardList(yardList);
        }
        return Collections.emptyList();
    }

    @GetMapping("/yard")
    public List<YardDto> getYards() {
        List<Yard> yardList = yardService.getAllYards();
        return yardDtoService.processOutgoingYardList(yardList);
    }

    @DeleteMapping("/yard")
    public ResponseEntity<String> deleteYards() {
        // TODO: do something with this - v.N
        return ResponseEntity.ok("Doesn't do anything yet - May not need this");
    }

    @GetMapping("/yard/{id}")
    public YardDto getYard(@PathVariable String id) {
        Optional<Yard> optionalYard = yardService.getYardById(Long.parseLong(id));
        if (optionalYard.isPresent()) {
            return yardDtoService.processOutgoingYard(optionalYard.get());
        }
        else { throw new NoSuchElementException("No such element found"); }
    }

    @PostMapping("/yard")
    public ResponseEntity<YardDto> postYard(@RequestBody YardDto yardDto) {
        Yard yard = yardDtoService.processIncomingYardDto(yardDto, false);
        long yardId = yardService.saveYard(yard);
        YardDto recreatedYardDto = yardDtoService.addId(yardDto, yardId);
        return ResponseEntity.created(URI.create("/api/yard/" + yardId)).body(recreatedYardDto);
    }

    @PatchMapping("/yard/{id}")
    public ResponseEntity<YardDto> patchYard(@PathVariable String id, @RequestBody YardDto yardDto) {
        Yard yard = yardDtoService.processIncomingYardDto(yardDto, true);
        Yard returnedYard = yardService.updateYard(id, yard);
        YardDto returnedYardDto = yardDtoService.processOutgoingYard(returnedYard);
        return ResponseEntity.ok(returnedYardDto);
    }

    @DeleteMapping("/yard/{id}")
    public ResponseEntity<String> deleteYard(@PathVariable String id) {
        yardService.deleteYardById(Long.parseLong(id));
        return ResponseEntity.noContent().build();
    }
}
