package com.example.cms_backend.Repositories;

import com.example.cms_backend.Model.Entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
