package com.example.TroEasyAPI.security;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.example.TroEasyAPI.common.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401

        ApiResponse<Void> apiResponse = ApiResponse.error(
                401,
                "Bạn chưa đăng nhập hoặc Token không hợp lệ!",
                request.getRequestURI()
        );

        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
    }
}
