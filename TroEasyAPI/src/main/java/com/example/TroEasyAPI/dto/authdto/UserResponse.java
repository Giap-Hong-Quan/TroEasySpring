package com.example.TroEasyAPI.dto.authdto;

import java.time.LocalDateTime;

public record UserResponse(
        Long id,
        String fullName,
        String username,
        LocalDateTime createdAt
) {}
