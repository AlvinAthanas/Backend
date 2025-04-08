package com.example.cms_backend.Security;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost") // Allow frontend requests
public class SecurityController {
    @GetMapping("/open")
    public String open() {
        return "open";
    }

    @GetMapping("/closed")
    public String closed() {
        return "closed";
    }

    @PreAuthorize("hasRole('superuser')")
    @GetMapping("/special")
    public String special() {
        return "special";
    }

    @PreAuthorize("hasRole('superuser') or hasRole('basicuser')")
    @GetMapping("/basic")
    public String basic() {
        return "basic";
    }
}
