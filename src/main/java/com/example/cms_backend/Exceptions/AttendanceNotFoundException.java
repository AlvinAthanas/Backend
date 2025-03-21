package com.example.cms_backend.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class AttendanceNotFoundException extends RuntimeException {
    public AttendanceNotFoundException() {
        super(ErrorMessages.ATTENDANCE_NOT_FOUND.getMessage());
    }
}
