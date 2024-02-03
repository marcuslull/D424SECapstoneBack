package com.mybackyard.backend.service.interfaces;

import com.mybackyard.backend.model.Note;

import java.util.List;
import java.util.Optional;

public interface NoteService {
    Optional<Note> getNoteById(Long noteId);

    List<Note> getAllNotes();

    long saveNote(Note note);

    Note updateNote(String id, Note note);

    void deleteNoteById(long id);
}
