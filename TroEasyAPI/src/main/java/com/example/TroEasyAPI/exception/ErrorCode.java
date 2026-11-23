package com.example.TroEasyAPI.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter 
@RequiredArgsConstructor 
public enum ErrorCode {

    // 400 - Bad Request: Request không hợp lệ
    BAD_REQUEST(400, "Bad request"),
    VALIDATION_ERROR(400, "Validation failed"),

    // 401 - Unauthorized: Chưa xác thực
    UNAUTHORIZED(401, "Unauthorized"),
    INVALID_TOKEN(401, "Invalid token"),
    TOKEN_EXPIRED(401, "Token expired"),

    // 403 - Forbidden: Không có quyền truy cập
    FORBIDDEN(403, "Forbidden"),

    // 404 - Not Found: Không tìm thấy tài nguyên
    NOT_FOUND(404, "Resource not found"),

    // 405 - Method Not Allowed: Gọi sai HTTP Method
    METHOD_NOT_ALLOWED(405, "Method not allowed"),

    // 409 - Conflict: Dữ liệu bị xung đột hoặc trùng lặp
    CONFLICT(409, "Resource already exists"),

    // 415 - Unsupported Media Type: Kiểu dữ liệu gửi lên không được hỗ trợ
    UNSUPPORTED_MEDIA_TYPE(415, "Unsupported media type"),

    // 429 - Too Many Requests: Gửi quá nhiều request
    TOO_MANY_REQUESTS(429, "Too many requests"),

    // 500 - Internal Server Error: Lỗi nội bộ máy chủ
    INTERNAL_SERVER_ERROR(500, "Internal server error"),

    // 503 - Service Unavailable: Máy chủ tạm thời không khả dụng
    SERVICE_UNAVAILABLE(503, "Service unavailable");

    private final int status;     // Mã trạng thái HTTP
    private final String message; // Thông báo lỗi mặc định
}