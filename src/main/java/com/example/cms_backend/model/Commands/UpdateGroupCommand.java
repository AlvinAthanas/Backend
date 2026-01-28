package com.example.cms_backend.model.Commands;

import com.example.cms_backend.model.Entities.Group;

public class UpdateGroupCommand {
    private Long id;
    private Group group;

    public UpdateGroupCommand(Long id, Group group) {
        this.id = id;
        this.group = group;
    }

    public Long getId() {
        return id;
    }

    public Group getGroup() {
        return group;
    }
}
