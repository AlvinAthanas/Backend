package com.example.cms_backend.services.ProjectServices;

import com.example.cms_backend.abstractions.Query;
import com.example.cms_backend.repositories.ProjectRepository;
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
