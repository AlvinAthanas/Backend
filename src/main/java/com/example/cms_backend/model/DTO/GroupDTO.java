package com.example.cms_backend.model.DTO;

import lombok.Getter;


import com.example.cms_backend.model.Entities.Group;
import lombok.Setter;

@Getter
@Setter
public class GroupDTO {
    private Long id;
    private String name;
    private String description;
    private Long kandaId;

    public GroupDTO(Group group) {
        this.id = group.getId();
        this.name = group.getName();
        this.description = group.getDescription();
        this.kandaId = group.getKandaId();
    }

    public GroupDTO(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}

