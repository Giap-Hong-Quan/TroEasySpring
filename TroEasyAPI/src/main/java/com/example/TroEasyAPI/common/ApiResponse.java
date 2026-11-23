package com.example.TroEasyAPI.common;

import java.time.Instant;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiResponse<T>(
        boolean success,
        int statusCode,
        String message,
        T result,
        String timestamp,
        String path) {

    // ✅ Success CÓ data
    public static <T> ApiResponse<T> success(String message, T result) {
        return new ApiResponse<>(true, 200, message, result, Instant.now().toString(), null);
    }

    // ✅ Success CÓ data + path
    public static <T> ApiResponse<T> success(String message, T result, String path) {
        return new ApiResponse<>(true, 200, message, result, Instant.now().toString(), path);
    }

    // ✅ Success KHÔNG data (Delete, update...)
    public static ApiResponse<Void> success(String message) {
        return new ApiResponse<>(true, 200, message, null, Instant.now().toString(), null);
    }

    // ❌ Error cơ bản
    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(false, 400, message, null, Instant.now().toString(), null);
    }

    // ❌ Error chuẩn RESTful dùng trong GlobalExceptionHandler
    public static <T> ApiResponse<T> error(int statusCode, String message, String path) {
        return new ApiResponse<>(false, statusCode, message, null, Instant.now().toString(), path);
    }

    // ❌ Error trả về danh sách chi tiết (dùng cho Validation)
    public static <T> ApiResponse<T> error(int statusCode, String message, T result, String path) {
        return new ApiResponse<>(false, statusCode, message, result, Instant.now().toString(), path);
    }
}