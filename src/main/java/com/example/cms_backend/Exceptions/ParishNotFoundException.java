package com.example.cms_backend.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ParishNotFoundException extends RuntimeException {
    public ParishNotFoundException() {
        super(ErrorMessages.PARISH_NOT_FOUND.getMessage());
    }
}
