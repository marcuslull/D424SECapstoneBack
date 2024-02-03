package com.mybackyard.backend.dto.service.interfaces;

import com.mybackyard.backend.dto.model.NoteDto;
import com.mybackyard.backend.model.Note;

import java.util.List;

public interface NoteDtoService {
    List<NoteDto> processOutgoingNoteList(List<Note> noteList);

    Note processIncomingNoteDto(NoteDto noteDto, boolean isFromPatch);

    NoteDto addId(NoteDto noteDto, long noteId);

    NoteDto processOutgoingNote(Note returnedNote);
}
