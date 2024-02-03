package com.mybackyard.backend.service.implementation;

import com.mybackyard.backend.model.Note;
import com.mybackyard.backend.repository.NoteRepository;
import com.mybackyard.backend.service.interfaces.ApiKeyService;
import com.mybackyard.backend.service.interfaces.CompareAndUpdate;
import com.mybackyard.backend.service.interfaces.NoteService;
import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;
    private final CompareAndUpdate compareAndUpdate;
    private final ApiKeyService apiKeyService;

    public NoteServiceImpl(NoteRepository noteRepository, CompareAndUpdate compareAndUpdate, ApiKeyService apiKeyService) {
        this.noteRepository = noteRepository;
        this.compareAndUpdate = compareAndUpdate;
        this.apiKeyService = apiKeyService;
    }

    @Override
    public List<Note> getAllNotes() {
        long principalId = apiKeyService.matchKeyToUserId(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        return noteRepository.findNotesByUserId(principalId);
    }

    @Override
    public Optional<Note> getNoteById(Long noteId) {
        long principalId = apiKeyService.matchKeyToUserId(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        return noteRepository.findNoteByNoteIdAndUserId(noteId, principalId);
    }

    @Override
    public long saveNote(Note note) {
        long principalId = apiKeyService.matchKeyToUserId(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        note.setUserId(principalId);
        return noteRepository.save(note).getNoteId();
    }

    @Override
    @Transactional
    public void deleteNoteById(long id) {
        long principalId = apiKeyService.matchKeyToUserId(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        noteRepository.deleteNoteByNoteIdAndUserId(id, principalId);
    }

    @Override
    public Note updateNote(String id, Note note) {
        long principalId = apiKeyService.matchKeyToUserId(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        note.setNoteId(Long.parseLong(id));
        Note baseNote = noteRepository.findNoteByNoteIdAndUserId(Long.valueOf(id), principalId)
                .orElseThrow(() -> new NoSuchElementException("Note not found with id: " + id));
        try {
            return noteRepository.save(compareAndUpdate.updateNote(baseNote, note));
        }
        catch (Exception e) {
            // TODO: figure out what to do with these exceptions - v.N
        }
        return baseNote;
    }
}
