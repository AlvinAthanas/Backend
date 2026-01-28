package com.example.cms_backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class DioceseNotFoundException extends RuntimeException {
    public DioceseNotFoundException() {
        super(ErrorMessages.DIOCESE_NOT_FOUND.getMessage());
    }
}
