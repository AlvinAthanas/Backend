package com.example.cms_backend.Model.Commands;

import lombok.Getter;

@Getter
public class GroupNameDescriptionCommand {
    private String groupName;
    private String description;

    public GroupNameDescriptionCommand(String description, String groupName) {
        this.groupName = groupName;
        this.description = description;
    }
}
