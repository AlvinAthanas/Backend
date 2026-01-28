package com.example.cms_backend.abstractions;

import org.springframework.http.ResponseEntity;

public interface Query <I,O>{
    public ResponseEntity<O> execute(I input);
}
