package com.example.cms_backend.Services.ProjectServices;

import com.example.cms_backend.Abstractions.Query;
import com.example.cms_backend.Repositories.ProjectRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CountProjectsPerParishService implements Query<Long, Long> {

    private final ProjectRepository projectRepository;

    public CountProjectsPerParishService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public ResponseEntity<Long> execute(Long parishId) {
        long count = projectRepository.countByParishId(parishId);
        return ResponseEntity.ok(count);
    }
}
