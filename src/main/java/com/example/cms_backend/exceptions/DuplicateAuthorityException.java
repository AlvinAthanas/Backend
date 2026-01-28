package com.example.cms_backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class DuplicateAuthorityException extends RuntimeException {
    public DuplicateAuthorityException(String authorityName) {
        super("Authority '" + authorityName + "' cannot be both granted and denied");
    }
}