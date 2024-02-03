package com.mybackyard.backend.dto.service.implementation;

import com.mybackyard.backend.dto.mapper.NoteDtoMapper;
import com.mybackyard.backend.dto.model.NoteDto;
import com.mybackyard.backend.dto.service.interfaces.NoteDtoService;
import com.mybackyard.backend.model.Note;
import com.mybackyard.backend.model.Yard;
import com.mybackyard.backend.validation.interfaces.DtoValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class NoteDtoServiceImplTest {

    private NoteDtoMapper noteDtoMapper;
    private DtoValidator dtoValidator;
    private NoteDtoService noteDtoService;

    @BeforeEach
    void setUp() {
        noteDtoMapper = mock(NoteDtoMapper.class);
        dtoValidator = mock(DtoValidator.class);
        noteDtoService = new NoteDtoServiceImpl(noteDtoMapper,dtoValidator);
    }

    @Test
    void processOutgoingNoteList() {
    }

    @Test
    void processOutgoingNote() {
    }

    @Test
    void processIncomingNoteDto() {
        NoteDto noteDto = new NoteDto(1,"comment", 0, 1, 0);
        NoteDto badNoteDto = new NoteDto(1,"", 0, 1, 0);
        Note note = new Note();
        note.setNoteId(1);
        note.setComment("name");
        note.setYard(new Yard());

        when(dtoValidator.isWellFormed(noteDto)).thenReturn(true);
        when(dtoValidator.isWellFormed(badNoteDto)).thenReturn(false);
        when(noteDtoMapper.dtoToNote(noteDto, false)).thenReturn(note);

        assertEquals(note, noteDtoService.processIncomingNoteDto(noteDto, false));
        assertThrows(RuntimeException.class, () -> noteDtoService.processIncomingNoteDto(badNoteDto, false));
    }

    @Test
    void addId() {
        NoteDto noteDto = new NoteDto(1,"comment", 0, 1, 0);
        NoteDto baseNoteDto = new NoteDto(0,"comment", 0, 1, 0);
        long noteId = 1;
        assertEquals(noteDto, noteDtoService.addId(baseNoteDto, noteId));
    }
}