package com.example.TroEasyAPI.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        // 1. Lấy token từ Header Authorization
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); // Cắt lấy chuỗi token thật
            try {
                Claims claims = jwtTokenProvider.verifyToken(token);
                String username = claims.getSubject();
                // Token xịn -> Báo cho Spring Security biết user này đã đăng nhập
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username,
                        null, null);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                // Token đểu hoặc hết hạn -> Xóa trắng xác thực, Spring sẽ chặn trả về lỗi
                SecurityContextHolder.clearContext();
            }
        }
        // Cho request đi tiếp
        filterChain.doFilter(request, response);
    }
}
