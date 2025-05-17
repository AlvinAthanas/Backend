package com.example.cms_backend.Model.Commands;

import lombok.Getter;

@Getter
public class AssignAuthorityCommand {
    private Long id;
    private String authorityName;

    public AssignAuthorityCommand(Long id, String authorityName) {
        this.id = id;
        this.authorityName = authorityName;
    }
}