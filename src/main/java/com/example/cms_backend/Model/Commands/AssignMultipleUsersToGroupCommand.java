package com.example.cms_backend.Model.Commands;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AssignMultipleUsersToGroupCommand {
    private List<Long> userIds;
    private Long groupId;
}
