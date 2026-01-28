package com.example.hms_backend.Exceptions;

import com.example.cms_backend.exceptions.ErrorMessages;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ProjectNotFoundException extends RuntimeException {
    public ProjectNotFoundException() {
        super(ErrorMessages.PROJECT_NOT_FOUND.getMessage());
    }
}
