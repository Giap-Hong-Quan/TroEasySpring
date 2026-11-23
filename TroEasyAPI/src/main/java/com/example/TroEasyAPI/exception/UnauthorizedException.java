package com.example.TroEasyAPI.exception;

// Chưa xác thực 401
public class UnauthorizedException extends BaseException {
    public UnauthorizedException(String message) {
        super(ErrorCode.UNAUTHORIZED, message);
    }
}
