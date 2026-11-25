package com.example.TroEasyAPI.dto.areadto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AreaRequest(
        @NotBlank(message = "Tên khu trọ không được để trống")
        @Size(max = 150, message = "Tên khu trọ tối đa 150 ký tự")
        String name,

        @NotBlank(message = "Địa chỉ không được để trống")
        @Size(max = 255, message = "Địa chỉ tối đa 255 ký tự")
        String address
) {}
