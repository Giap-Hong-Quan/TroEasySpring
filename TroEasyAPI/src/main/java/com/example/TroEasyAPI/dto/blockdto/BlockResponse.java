package com.example.TroEasyAPI.dto.blockdto;

import java.time.LocalDateTime;

public record BlockResponse(
        Long id,
        String name,
        Long areaId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
