package com.example.cms_backend.model.Commands;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

/**
 * Command to create or update a role-authority rule.
 */
@Data
public class CreateRoleAuthorityRuleCommand {
    private String roleName;
    private boolean overrideDefault = true;
    private Set<String> grantedAuthorities = new HashSet<>();
    private Set<String> deniedAuthorities = new HashSet<>();
}