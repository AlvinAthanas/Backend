package com.example.cms_backend.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class FeedbackNotFoundException extends RuntimeException {
    public FeedbackNotFoundException() {
        super(ErrorMessages.FEEDBACK_NOT_FOUND.getMessage());
    }
}
