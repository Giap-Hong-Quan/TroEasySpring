package com.example.TroEasyAPI.exception;

// Không có quyền truy cập 403
public class ForbiddenException extends BaseException {
    public ForbiddenException(String message) {
        super(ErrorCode.FORBIDDEN, message);
    }
}
