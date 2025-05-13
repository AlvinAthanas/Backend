package com.example.cms_backend.Model.Commands;

import com.example.cms_backend.Model.Entities.Parish;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FavParishCommand {
    private Long userId;
    private Long parishId;
    private Boolean isLiked;
}

