package com.example.TroEasyAPI.dto.authdto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank(message = "Họ và tên không được để trống")
        String fullName,
        @NotBlank(message = "Tên đăng nhập không được để trống")
        String username,
        @NotBlank(message = "Mật khẩu không được để trống")
        @Size(min = 2, message = "Mật khẩu phải từ 6 ký tự trở lên")
        String password
) {}
