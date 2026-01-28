package com.example.cms_backend.model.Commands;

import com.example.cms_backend.model.Entities.Role;

public class UpdateRoleCommand {
    private Long id;
    private Role role;

    public UpdateRoleCommand(Long id, Role role) {
        this.id = id;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public Role getRole() {
        return role;
    }
}
