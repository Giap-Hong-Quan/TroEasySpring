package com.example.TroEasyAPI.exception;
// Trùng dữ liệu 409
public class ConflictException extends BaseException {
    public ConflictException(String message){
        super(ErrorCode.CONFLICT,message);
    }
}
