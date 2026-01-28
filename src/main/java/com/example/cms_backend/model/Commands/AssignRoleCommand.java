package com.example.cms_backend.model.Commands;

import lombok.Getter;

@Getter
public class AssignRoleCommand {
    private Long id;
    private String roleName;

    public AssignRoleCommand(Long id, String roleName) {
        this.id = id;
        this.roleName = roleName;
    }
}
