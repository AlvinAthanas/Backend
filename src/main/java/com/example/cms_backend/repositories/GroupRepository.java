package com.example.cms_backend.repositories;

import com.example.cms_backend.model.Entities.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    List<Group> findByNameContaining(String name);
    List<Group> findByDescription(String description);
    Long countByDescription(String description);
    List<Group> findByNameContainingIgnoreCaseAndDescription(String name, String description);



}
