package com.example.cms_backend.Model.Commands;

import lombok.Data;
import lombok.Getter;

@Getter
public class SearchGroupNameByDescriptionCommand {
    private String groupName;
    private String description;

    public SearchGroupNameByDescriptionCommand(String groupName, String description) {
        this.groupName = groupName;
        this.description = description;
    }
}
