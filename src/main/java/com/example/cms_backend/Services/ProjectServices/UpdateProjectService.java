package com.example.cms_backend.Services.ProjectServices;

import com.example.cms_backend.Abstractions.Command;

import com.example.cms_backend.Model.Commands.UpdateProjectCommand;
import com.example.cms_backend.Model.Entities.Project;
import com.example.cms_backend.Repositories.ProjectRepository;
import com.example.hms_backend.Exceptions.ProjectNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UpdateProjectService implements Command<UpdateProjectCommand, Project> {
    private final ProjectRepository projectRepository;

    public UpdateProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public ResponseEntity<Project> execute(UpdateProjectCommand command) {
        Optional<Project> projectOptional = projectRepository.findById(command.getId());
        if (projectOptional.isPresent()) {
            Project existing = projectOptional.get();
            Project update = command.getProject();

            existing.setName(update.getName());
            existing.setDescription(update.getDescription());
            existing.setBudget(update.getBudget());
            existing.setCollected(update.getCollected());

            // Only update image if a new one was provided
            if (update.getFeaturedImage() != null && update.getFeaturedImage().length > 0) {
                existing.setFeaturedImage(update.getFeaturedImage());
            }

            projectRepository.save(existing);
            return ResponseEntity.ok(existing);
        }
        throw new ProjectNotFoundException();
    }

}
