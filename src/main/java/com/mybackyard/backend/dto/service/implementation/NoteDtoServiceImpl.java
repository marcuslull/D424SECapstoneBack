package com.mybackyard.backend.dto.service.implementation;

import com.mybackyard.backend.dto.mapper.NoteDtoMapper;
import com.mybackyard.backend.dto.model.NoteDto;
import com.mybackyard.backend.dto.service.interfaces.NoteDtoService;
import com.mybackyard.backend.model.Note;
import com.mybackyard.backend.validation.interfaces.DtoValidator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteDtoServiceImpl implements NoteDtoService {

    private final NoteDtoMapper noteDtoMapper;
    private final DtoValidator dtoValidator;

    public NoteDtoServiceImpl(NoteDtoMapper noteDtoMapper, DtoValidator dtoValidator) {
        this.noteDtoMapper = noteDtoMapper;
        this.dtoValidator = dtoValidator;
    }

    @Override
    public List<NoteDto> processOutgoingNoteList(List<Note> noteList) {
        return noteDtoMapper.notesToDtos(noteList);
    }

    @Override
    public NoteDto processOutgoingNote(Note note) {
        return noteDtoMapper.noteToDto(note);
    }

    @Override
    public Note processIncomingNoteDto(NoteDto noteDto, boolean isFromPatch) {
        if (!dtoValidator.isWellFormed(noteDto)) {
            throw new RuntimeException("Malformed DTO");
        }
        return noteDtoMapper.dtoToNote(noteDto, isFromPatch);
    }

    @Override
    public NoteDto addId(NoteDto noteDto, long noteId) {
        return new NoteDto(noteId, noteDto.comment(), noteDto.animalId(), noteDto.yardId(), noteDto.plantId());
    }
}
