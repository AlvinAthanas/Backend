package com.example.cms_backend.Exceptions;

public class AdminNotValidException extends RuntimeException {
    public AdminNotValidException(String message) {
        super(message);
    }
}
