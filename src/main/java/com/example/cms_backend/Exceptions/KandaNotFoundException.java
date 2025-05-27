package com.example.cms_backend.Exceptions;

public class KandaNotFoundException extends RuntimeException {
    public KandaNotFoundException() {
        super(ErrorMessages.KANDA_NOT_FOUND.getMessage());
    }
}
