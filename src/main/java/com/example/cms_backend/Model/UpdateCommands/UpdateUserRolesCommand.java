package com.example.cms_backend.Model.UpdateCommands;


import java.util.Set;

public class UpdateUserRolesCommand {
    private Long id;
    private Set<String> roleNames;

    public UpdateUserRolesCommand(Long id, Set<String> roleNames) {
        this.id = id;
        this.roleNames = roleNames;
    }

    public Long getId() {
        return id;
    }

    public Set<String> getRoleNames() {
        return roleNames;
    }
}

