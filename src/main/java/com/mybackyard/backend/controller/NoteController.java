package com.mybackyard.backend.controller;

import com.mybackyard.backend.dto.model.NoteDto;
import com.mybackyard.backend.dto.service.interfaces.NoteDtoService;
import com.mybackyard.backend.model.Note;
import com.mybackyard.backend.service.interfaces.NoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class NoteController {

    private final NoteService noteService;
    private final NoteDtoService noteDtoService;

    public NoteController(NoteService noteService, NoteDtoService noteDtoService){
        this.noteService = noteService;
        this.noteDtoService = noteDtoService;
    }

    @GetMapping("/note")
    public List<NoteDto> getNotes() {
        List<Note> noteList = noteService.getAllNotes();
        return noteDtoService.processOutgoingNoteList(noteList);
    }

    @DeleteMapping("/note")
    public ResponseEntity<String> deleteNotes() {
        // TODO: do something with this - v.N
        return ResponseEntity.ok("Doesn't do anything yet - May not need this");
    }

    @GetMapping("/note/{id}")
    public NoteDto getNote(@PathVariable String id) {
        Optional<Note> optionalNote = noteService.getNoteById(Long.parseLong(id));
        if (optionalNote.isPresent()) {
            return noteDtoService.processOutgoingNote(optionalNote.get());
        }
        else { throw new NoSuchElementException("No such element found"); }
    }

    @PostMapping("/note")
    public ResponseEntity<NoteDto> postNote(@RequestBody NoteDto noteDto) {
        Note note = noteDtoService.processIncomingNoteDto(noteDto, false);
        long noteId = noteService.saveNote(note);
        NoteDto recreatedNoteDto = noteDtoService.addId(noteDto, noteId);
        return ResponseEntity.created(URI.create("/api/note/" + noteId)).body(recreatedNoteDto);
    }

    @PatchMapping("/note/{id}")
    public ResponseEntity<NoteDto> patchNote(@PathVariable String id, @RequestBody NoteDto noteDto) {
        Note note = noteDtoService.processIncomingNoteDto(noteDto, true);
        Note returnedNote = noteService.updateNote(id, note);
        NoteDto returnedNoteDto = noteDtoService.processOutgoingNote(returnedNote);
        return ResponseEntity.ok(returnedNoteDto);
    }

    @DeleteMapping("/note/{id}")
    public ResponseEntity<String> deleteNote(@PathVariable String id) {
        noteService.deleteNoteById(Long.parseLong(id));
        return ResponseEntity.noContent().build();
    }
}
