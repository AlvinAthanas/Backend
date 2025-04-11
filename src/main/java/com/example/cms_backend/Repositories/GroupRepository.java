package com.example.cms_backend.Repositories;

import com.example.cms_backend.Model.Entities.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    List<Group> findByNameContaining(String name);
    Long countByDescription(String description);
}
