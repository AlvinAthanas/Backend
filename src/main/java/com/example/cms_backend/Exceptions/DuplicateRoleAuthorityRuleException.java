package com.example.cms_backend.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class DuplicateRoleAuthorityRuleException extends RuntimeException {
    public DuplicateRoleAuthorityRuleException(String roleName) {
        super("Authority rule already exists for role: " + roleName);
    }
}