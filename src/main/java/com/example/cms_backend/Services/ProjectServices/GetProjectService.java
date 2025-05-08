package com.example.cms_backend.Services.ProjectServices;

import com.example.cms_backend.Abstractions.Query;
import com.example.cms_backend.Model.Entities.Project;
import com.example.cms_backend.Repositories.ProjectRepository;
import com.example.hms_backend.Exceptions.ProjectNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GetProjectService implements Query<Long, Project> {
    private final ProjectRepository projectRepository;

    public GetProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public ResponseEntity<Project> execute(Long id) {
        Optional<Project> projectOptional = projectRepository.findById(id);
        if (projectOptional.isPresent()) {
            return ResponseEntity.ok().body(projectOptional.get());
        }
        throw new ProjectNotFoundException();
    }
}
