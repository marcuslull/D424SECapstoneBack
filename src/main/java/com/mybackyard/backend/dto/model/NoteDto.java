package com.mybackyard.backend.dto.model;

public record NoteDto(
        long id,
        String comment,
        long animalId,
        long yardId,
        long plantId) {}
