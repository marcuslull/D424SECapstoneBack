package com.mybackyard.backend.dto.model;

import java.util.List;

public record YardDto(
        long id,
        String name,
        String hardinessZone,
        String soilType,
        String sunExposure,
        String yardSubType,
        List<Long> noteIds,
        List<Long> animalIds,
        List<Long> plantIds,
        long userId) {}
