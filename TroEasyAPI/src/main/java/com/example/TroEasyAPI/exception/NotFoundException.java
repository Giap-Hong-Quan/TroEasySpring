package com.example.TroEasyAPI.exception;

// Không tìm thấy dữ liệu 404
public class NotFoundException extends BaseException {
    public NotFoundException(String message) {
        super(ErrorCode.NOT_FOUND, message);
    }

}
