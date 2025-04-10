package com.example.cms_backend.Repositories;

import com.example.cms_backend.Model.Entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    public List<Role> findByNameContaining(String name);

    Optional<Role> findByName(String roleName);
}
