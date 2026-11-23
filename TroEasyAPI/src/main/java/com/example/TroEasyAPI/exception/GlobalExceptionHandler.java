package com.example.TroEasyAPI.exception;

import com.example.TroEasyAPI.common.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

// Bắt và chuẩn hóa toàn bộ exception trong ứng dụng
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. Bắt tất cả các Exception nghiệp vụ do mình tự định nghĩa (BadRequest, NotFound, Conflict, Forbidden, Unauthorized...)
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ApiResponse<Void>> handleBaseException(BaseException ex, HttpServletRequest request) {
        int status = ex.getErrorCode().getStatus();
        return ResponseEntity
                .status(status)
                .body(ApiResponse.error(status, ex.getMessage(), request.getRequestURI()));
    }

    // 2. Bắt lỗi Validation khi dùng @Valid ở DTO Request (Gửi thiếu trường, sai định dạng...)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationException(
            MethodArgumentNotValidException ex, HttpServletRequest request) {

        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(HttpStatus.BAD_REQUEST.value(), "Dữ liệu không hợp lệ", errors, request.getRequestURI()));
    }

    // 3. Bắt lỗi gửi sai định dạng JSON hoặc sai kiểu dữ liệu (ví dụ: cần số Long mà gửi chữ String)
    @ExceptionHandler({HttpMessageNotReadableException.class, MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ApiResponse<Void>> handleBadRequestSyntax(Exception ex, HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(HttpStatus.BAD_REQUEST.value(), "Định dạng dữ liệu gửi lên không hợp lệ", request.getRequestURI()));
    }

    // 4. Bắt tất cả các lỗi sập hệ thống không lường trước được (500 Internal Server Error)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleUnwantedException(Exception ex, HttpServletRequest request) {
        // Ghi log để lập trình viên xem lỗi thật trên console/file log
        ex.printStackTrace();

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Lỗi hệ thống: Vui lòng thử lại sau!",
                        request.getRequestURI()
                ));
    }
}
