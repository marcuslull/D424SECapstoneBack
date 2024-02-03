package com.mybackyard.backend.dto.model;

import java.util.List;

public record AnimalDto(
        long id,
        String name,
        String subType,
        String dietType,
        String nativeAreaType,
        List<Long> noteIds,
        List<Long> imageIds,
        long yardId) {}
