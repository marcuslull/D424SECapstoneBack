package com.mybackyard.backend.dto.mapper;

import com.mybackyard.backend.dto.model.NoteDto;
import com.mybackyard.backend.model.Note;
import com.mybackyard.backend.service.interfaces.AnimalService;
import com.mybackyard.backend.service.interfaces.PlantService;
import com.mybackyard.backend.service.interfaces.YardService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Component
public class NoteDtoMapper {

    private final YardService yardService;
    private final PlantService plantService;
    private final AnimalService animalService;

    public NoteDtoMapper(YardService yardService, PlantService plantService, AnimalService animalService) {
        this.yardService = yardService;
        this.plantService = plantService;
        this.animalService = animalService;
    }

    public List<NoteDto> notesToDtos(List<Note> noteList) {
        List<NoteDto> noteDtos = new ArrayList<>();
        for (Note note : noteList) {
            noteDtos.add(convertNoteToDto(note));
        }
        return noteDtos;
    }

    public NoteDto noteToDto(Note note) {
        return convertNoteToDto(note);
    }

    public Note dtoToNote(NoteDto noteDto, Boolean isFromPatch) {
        return convertDtoToNote(noteDto, isFromPatch);
    }

    private Note convertDtoToNote(NoteDto noteDto, Boolean isFromPatch) {
        Note note = new Note();

        if (isFromPatch) {
            if (noteDto.comment() != null) note.setComment(noteDto.comment());
            return note;
        }

        note.setComment(noteDto.comment());
        if (noteDto.yardId() != 0) note.setYard(yardService.getYardById(noteDto.yardId())
                .orElseThrow(() -> new NoSuchElementException("Yard id associated with note does not exist")));
        if (noteDto.plantId() != 0) note.setPlant(plantService.getPlantById(noteDto.plantId())
                .orElseThrow(() -> new NoSuchElementException("Plant id associated with note does not exist")));
        if (noteDto.animalId() != 0) note.setAnimal(animalService.getAnimalById(noteDto.animalId())
                .orElseThrow(() -> new NoSuchElementException("Animal id associated with note does not exist")));
        return note;
    }

    private NoteDto convertNoteToDto(Note note) {
        long yardId = (note.getYard() == null) ? 0 : note.getYard().getYardId();
        long plantId = (note.getPlant() == null) ? 0 : note.getPlant().getPlantId();
        long animalId = (note.getAnimal() == null) ? 0 : note.getAnimal().getAnimalId();

        return new NoteDto(note.getNoteId(), note.getComment(), animalId, yardId, plantId);
    }
}
