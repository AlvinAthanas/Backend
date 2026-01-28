package com.example.cms_backend.model.Commands;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RemoveGroupCommand {
    private Long userId;
    private Long groupId;
}
