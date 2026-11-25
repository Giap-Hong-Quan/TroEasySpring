package com.example.TroEasyAPI.dto.blockdto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BlockRequest(
        @NotBlank(message = "Tên dãy không được để trống")
        @Size(max = 100, message = "Tên dãy tối đa 100 ký tự")
        String name,

        @NotNull(message = "ID khu trọ không được để trống")
        Long areaId
) {}
