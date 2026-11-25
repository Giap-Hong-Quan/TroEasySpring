package com.example.TroEasyAPI.dto.areadto;

import java.time.LocalDateTime;

public record AreaResponse(
        Long id,
        String name,
        String address,
        Boolean isActive,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
