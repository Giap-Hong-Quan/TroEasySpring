package com.example.TroEasyAPI.exception;

// Request không hợp lệ 400
public class BadRequestException extends BaseException {
    public BadRequestException(String message) {
        super(ErrorCode.BAD_REQUEST, message);
    }
}
