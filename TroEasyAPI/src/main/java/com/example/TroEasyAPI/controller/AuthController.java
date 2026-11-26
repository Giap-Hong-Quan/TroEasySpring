package com.example.TroEasyAPI.controller;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.TroEasyAPI.common.ApiResponse;
import com.example.TroEasyAPI.dto.authdto.AuthResponse;
import com.example.TroEasyAPI.dto.authdto.LoginRequest;
import com.example.TroEasyAPI.dto.authdto.RegisterRequest;
import com.example.TroEasyAPI.dto.authdto.UserResponse;
import com.example.TroEasyAPI.exception.UnauthorizedException;
import com.example.TroEasyAPI.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@Valid @RequestBody RegisterRequest request) {
        AuthResponse response = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Đăng ký tài khoản thành công!", response));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(ApiResponse.success("Đăng nhập thành công!", response));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> getCurrentUser(Principal principal) {
        if (principal == null) {
            throw new UnauthorizedException("Vui lòng đăng nhập để tiếp tục!");
        }
        UserResponse userResponse = authService.getCurrentUser(principal.getName());
        return ResponseEntity.ok(ApiResponse.success("Lấy thông tin tài khoản thành công!", userResponse));
    }
}
