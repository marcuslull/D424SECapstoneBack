package com.mybackyard.backend.dto.model;

public record ImageDto(
        long id,
        String location,
        long yardId,
        long plantId,
        long animalId) {}
