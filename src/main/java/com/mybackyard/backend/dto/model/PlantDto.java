package com.mybackyard.backend.dto.model;

import java.util.List;

public record PlantDto(
        long id,
        String name,
        String hardinessZone,
        String nativeAreaType,
        String plantSubType,
        String soilType,
        String sunExposure,
        String wateringFrequency,
        List<Long> noteIds,
        List<Long> imageIds,
        long yardId) {}
