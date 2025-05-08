package com.example.cms_backend.Services.ProjectServices;

import com.example.cms_backend.Abstractions.Query;
import com.example.cms_backend.Model.Entities.Project;
import com.example.cms_backend.Repositories.ProjectRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetProjectsService implements Query<Void, List<Project>> {
    private final ProjectRepository projectRepository;

    public GetProjectsService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public ResponseEntity<List<Project>> execute(Void input) {
        List<Project> projects = projectRepository.findAll();
        return ResponseEntity.ok().body(projects);
    }
}
