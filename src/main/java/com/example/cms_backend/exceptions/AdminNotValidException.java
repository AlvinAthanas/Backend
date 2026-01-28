package com.example.cms_backend.exceptions;

public class AdminNotValidException extends RuntimeException {
    public AdminNotValidException(String message) {
        super(message);
    }
}
