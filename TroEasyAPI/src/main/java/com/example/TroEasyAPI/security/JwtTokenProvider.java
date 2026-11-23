package com.example.TroEasyAPI.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {
    @Value("${jwt.secret}")
    private String jwtSecret;
    @Value("${jwt.expiration}")
    private long jwtExpiration;

     // Helper tạo chìa khóa
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }
      // 1. TẠO TOKEN TỪ USERNAME
    public String generateToken(Long userId,String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpiration);
        return Jwts.builder()
                .subject(username) // Lưu username vào token
                .claim("userId", userId)
                .issuedAt( new Date())     // Ngày tạo
                .expiration(expiryDate) // Ngày hết hạn
                .signWith(getSigningKey()) // Ký bằng SecretKey
                .compact();
    }
    // 2. TƯƠNG ĐƯƠNG jwt.verify() TRONG NODE.JS
    // Vừa check hợp lệ, vừa lấy luôn Claims (username, data bên trong)
    // Nếu token giả mạo hoặc hết hạn -> thư viện tự NÉM EXCEPTION y hệt Node.js
    public Claims verifyToken(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
