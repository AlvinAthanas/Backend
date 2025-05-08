package com.example.cms_backend.Services.ProjectServices;

import com.example.cms_backend.Abstractions.Command;
import com.example.cms_backend.Model.Entities.Project;
import com.example.cms_backend.Repositories.ProjectRepository;
import com.example.hms_backend.Exceptions.ProjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeleteProjectService implements Command<Long, Void> {
    private final ProjectRepository projectRepository;

    public DeleteProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public ResponseEntity<Void> execute(Long id) {
        Optional<Project> projectOptional = projectRepository.findById(id);
        if (projectOptional.isPresent()) {
            projectRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        throw new ProjectNotFoundException();
    }
}
