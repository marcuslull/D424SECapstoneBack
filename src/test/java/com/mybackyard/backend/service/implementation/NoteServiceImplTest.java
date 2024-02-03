package com.mybackyard.backend.service.implementation;

import com.mybackyard.backend.model.Note;
import com.mybackyard.backend.repository.NoteRepository;
import com.mybackyard.backend.service.interfaces.ApiKeyService;
import com.mybackyard.backend.service.interfaces.CompareAndUpdate;
import com.mybackyard.backend.service.interfaces.NoteService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NoteServiceImplTest {

    private NoteRepository noteRepository;
    private CompareAndUpdate compareAndUpdate;
    private ApiKeyService apikeyService;
    private NoteService noteService;
    private MockedStatic<SecurityContextHolder> securityContextHolder;
    private SecurityContext securityContext;
    private Authentication authentication;
    private Principal principal;

    @BeforeEach
    void setUp() {
        noteRepository = mock(NoteRepository.class);
        compareAndUpdate = mock(CompareAndUpdate.class);
        apikeyService = mock(ApiKeyService.class);
        noteService = new NoteServiceImpl(noteRepository, compareAndUpdate, apikeyService);

        // below 9 lines are to mock the principalId
        securityContextHolder = mockStatic(SecurityContextHolder.class);
        securityContext = mock(SecurityContext.class);
        authentication = mock(Authentication.class);
        principal = mock(Principal.class);
        securityContextHolder.when(() -> SecurityContextHolder.getContext()).thenReturn(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(principal);
        when(principal.toString()).thenReturn("1");
        when(apikeyService.matchKeyToUserId(anyString())).thenReturn(1L);
    }

    // need this in order to create a new security context for each test
    @AfterEach
    void tearDown() {
        securityContextHolder.close();
    }

    @Test
    void getAllNotes() {
        long userId = 1L;
        long noteId = 1L;
        Note note = new Note();
        note.setNoteId(noteId);
        note.setUserId(userId);
        List<Note> listOfNotes = List.of(note);
        when(noteRepository.findNotesByUserId(userId)).thenReturn(listOfNotes);

        assertEquals(listOfNotes, noteService.getAllNotes());
    }

    @Test
    void getNoteById() {
        long userId = 1L;
        long noteId = 1L;
        Note note = new Note();
        note.setNoteId(noteId);
        note.setUserId(userId);
        Optional<Note> optionalNote = Optional.of(note);
        when(noteRepository.findNoteByNoteIdAndUserId(noteId, userId)).thenReturn(optionalNote);

        assertEquals(optionalNote, noteService.getNoteById(noteId));
    }

    @Test
    void saveNote() {
        Note note = new Note();
        long userId = 1L;
        long noteId = 1L;
        note.setNoteId(noteId);
        note.setUserId(userId);
        when(noteRepository.save(note)).thenReturn(note);

        assertEquals(noteId, noteService.saveNote(note));
    }

    @Test
    void deleteNoteById() {
    }

    @Test
    void updateNote() throws IllegalAccessException {
        long noteId = 1L;
        long userId = 1L;
        Note note = new Note();
        note.setNoteId(noteId);
        note.setUserId(userId);
        note.setComment("comment");
        Optional<Note> baseNote = Optional.of(note);
        Note patch = new Note();
        patch.setComment("new comment");
        Note updatedNote = new Note();
        updatedNote.setNoteId(noteId);
        updatedNote.setUserId(userId);
        updatedNote.setComment("new comment");
        when(noteRepository.findNoteByNoteIdAndUserId(noteId, userId)).thenReturn(baseNote);
        when(compareAndUpdate.updateNote(baseNote.get(), patch)).thenReturn(updatedNote);
        when(noteRepository.save(updatedNote)).thenReturn(updatedNote);

        assertEquals(updatedNote, noteService.updateNote(String.valueOf(noteId), patch));
    }
}