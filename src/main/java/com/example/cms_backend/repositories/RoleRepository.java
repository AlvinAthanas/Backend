package com.example.cms_backend.repositories;

import com.example.cms_backend.model.Entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    public List<Role> findByNameContaining(String name);

    Optional<Role> findByName(String roleName);

    Set<Role> findByNameIn(Set<String> names);  // Must use Set<String>


}
