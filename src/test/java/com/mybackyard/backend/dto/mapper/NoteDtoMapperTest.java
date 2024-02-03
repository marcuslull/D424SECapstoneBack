package com.mybackyard.backend.dto.mapper;

import com.mybackyard.backend.dto.model.NoteDto;
import com.mybackyard.backend.model.Note;
import com.mybackyard.backend.service.interfaces.AnimalService;
import com.mybackyard.backend.service.interfaces.PlantService;
import com.mybackyard.backend.service.interfaces.YardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class NoteDtoMapperTest {

    private YardService yardService;
    private PlantService plantService;
    private AnimalService animalService;
    private NoteDtoMapper noteDtoMapper;

    @BeforeEach
    void setUp() {
        yardService = mock(YardService.class);
        plantService = mock(PlantService.class);
        animalService = mock(AnimalService.class);
        noteDtoMapper = new NoteDtoMapper(yardService,plantService, animalService);
    }

    @Test
    void notesToDtos() {
        NoteDto noteDto = new NoteDto(1, "comment", 0,0,0);
        List<NoteDto> noteDtoList = List.of(noteDto);
        Note note = new Note();
        note.setNoteId(1);
        note.setComment("comment");
        List<Note> noteList = List.of(note);

        assertEquals(noteDtoList, noteDtoMapper.notesToDtos(noteList));
    }

    @Test
    void noteToDto() {
        NoteDto noteDto = new NoteDto(1, "comment", 0,0,0);
        Note note = new Note();
        note.setNoteId(1);
        note.setComment("comment");

        assertEquals(noteDto, noteDtoMapper.noteToDto(note));
    }

    @Test
    void dtoToNote() {
        NoteDto noteDto = new NoteDto(1, "comment", 0,0,0);
        Note note = new Note();
        note.setNoteId(1);
        note.setComment("comment");

        assertEquals(note.getComment(), noteDtoMapper.dtoToNote(noteDto, true).getComment());
        assertEquals(note.getComment(), noteDtoMapper.dtoToNote(noteDto, false).getComment());
    }
}