package com.example.cms_backend.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ContributionNotFoundException extends RuntimeException {
    public ContributionNotFoundException() {
        super(ErrorMessages.CONTRIBUTION_NOT_FOUND.getMessage());
    }
}
