package com.mybackyard.backend.repository;

import com.mybackyard.backend.model.Note;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface NoteRepository  extends CrudRepository<Note, Long> {
    List<Note> findNotesByUserId(long userId);
    Optional<Note> findNoteByNoteIdAndUserId(long noteId, long userId);
    void deleteNoteByNoteIdAndUserId(long noteId, long userId);
}
