package com.example.cms_backend.Services.ProjectServices;

import com.example.cms_backend.Abstractions.Command;
import com.example.cms_backend.Model.Entities.Project;
import com.example.cms_backend.Repositories.ProjectRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CreateProjectService implements Command<Project, Project> {
    private final ProjectRepository projectRepository;

    public CreateProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public ResponseEntity<Project> execute(Project project) {
        projectRepository.save(project);
        return ResponseEntity.status(HttpStatus.CREATED).body(project);
    }
}
