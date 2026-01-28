package com.example.cms_backend.model.Commands;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FavParishCommand {
    private Long userId;
    private Long parishId;
    private Boolean isLiked;
}

