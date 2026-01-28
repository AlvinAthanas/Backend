package com.example.cms_backend.model.Commands;

import lombok.Getter;

@Getter
public class AssignGroupCommand {
    private Long userId;
    private Long groupId;

    public AssignGroupCommand(Long userId, Long groupId) {
        this.userId = userId;
        this.groupId = groupId;
    }
}
