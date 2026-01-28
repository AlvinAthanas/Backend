package com.example.cms_backend.repositories;

import com.example.cms_backend.model.Entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Query("SELECT COUNT(p) FROM Project p WHERE p.parishId = :parishId")
    long countByParishId(@Param("parishId") Long parishId);

    @Query("SELECT p FROM Project p WHERE p.parishId = :parishId")
    List<Project> findByParishId(@Param("parishId") Long parishId);

}
