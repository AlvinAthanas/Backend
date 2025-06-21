package com.example.cms_backend.Model.Commands;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;

@Getter
public class SearchGroupNameByDescriptionCommand {
    private final String groupName;
    private final String description;
    private final HttpServletRequest request;

    public SearchGroupNameByDescriptionCommand(String groupName, String description, HttpServletRequest request) {
        this.groupName = groupName;
        this.description = description;
        this.request = request;
    }
}
