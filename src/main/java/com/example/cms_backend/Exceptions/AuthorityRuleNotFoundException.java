package com.example.cms_backend.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class AuthorityRuleNotFoundException extends RuntimeException {
    public AuthorityRuleNotFoundException() {
        super("Authority rule not found");
    }
    
    public AuthorityRuleNotFoundException(Long id) {
        super("Authority rule not found with id: " + id);
    }
    
    public AuthorityRuleNotFoundException(String roleName) {
        super("Authority rule not found for role: " + roleName);
    }
}