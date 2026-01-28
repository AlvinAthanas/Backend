package com.example.cms_backend.model.Commands;

import com.example.cms_backend.model.Entities.Project;

public class UpdateProjectCommand {
    private Long id;
    private Project project;

    public UpdateProjectCommand(Long id, Project project) {
        this.id = id;
        this.project = project;
    }

    public Long getId() {
        return id;
    }

    public Project getProject() {
        return project;
    }
}
