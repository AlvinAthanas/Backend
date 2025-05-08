package com.example.cms_backend.Model.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GroupDTO {
    private Long id;
    private String name;
    private String description;
}
