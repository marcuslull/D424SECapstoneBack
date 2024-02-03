package com.mybackyard.backend.dto.model;

import java.util.List;

public record UserDto(
        long id,
        String first,
        String last,
        String email,
        String apiKey,
        List<Long> yardIds) {}
