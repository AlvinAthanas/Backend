package com.example.cms_backend.Model.Commands;

import lombok.Data;

@Data
public class JoinCommunityRequestCommand {
    private Long userId;
    private Long groupId;
}

