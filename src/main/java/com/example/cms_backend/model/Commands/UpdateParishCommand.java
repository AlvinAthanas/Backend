package com.example.cms_backend.model.Commands;

import com.example.cms_backend.model.Entities.Parish;

public class UpdateParishCommand {
    private Long id;
    private Parish parish;

    public UpdateParishCommand(Long id, Parish parish) {
        this.id = id;
        this.parish = parish;
    }

    public Long getId() {
        return id;
    }

    public Parish getParish() {
        return parish;
    }
}
