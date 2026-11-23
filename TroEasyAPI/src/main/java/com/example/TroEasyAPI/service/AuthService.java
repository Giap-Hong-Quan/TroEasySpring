package com.example.TroEasyAPI.service;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.TroEasyAPI.dto.authdto.AuthResponse;
import com.example.TroEasyAPI.dto.authdto.LoginRequest;
import com.example.TroEasyAPI.dto.authdto.RegisterRequest;
import com.example.TroEasyAPI.entity.User;
import com.example.TroEasyAPI.exception.ConflictException;
import com.example.TroEasyAPI.exception.UnauthorizedException;
import com.example.TroEasyAPI.repository.UserRepository;
import com.example.TroEasyAPI.security.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.username())) {
            throw new ConflictException("Tên đăng nhập '" + request.username() + "' đã tồn tại!");
        }

        User user = new User();
        user.setFullName(request.fullName());
        user.setUsername(request.username());
        user.setPassword(passwordEncoder.encode(request.password())); // Mã hóa BCrypt mật khẩu
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        User savedUser = userRepository.save(user);
        String token = jwtTokenProvider.generateToken(savedUser.getId(), savedUser.getUsername());
        return AuthResponse.of(token);
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new UnauthorizedException("Tên đăng nhập hoặc mật khẩu không chính xác!"));

        // So khớp mật khẩu nhập vào với mật khẩu đã mã hóa trong DB
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new UnauthorizedException("Tên đăng nhập hoặc mật khẩu không chính xác!");
        }

        // Tạo token chứa userId và username
        String token = jwtTokenProvider.generateToken(user.getId(), user.getUsername());
        return AuthResponse.of(token);
    }
}
