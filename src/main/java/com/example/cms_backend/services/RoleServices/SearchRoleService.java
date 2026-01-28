package com.example.cms_backend.services.RoleServices;

import com.example.cms_backend.abstractions.Query;
import com.example.cms_backend.model.Entities.Role;
import com.example.cms_backend.repositories.RoleRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchRoleService implements Query<String, List<Role>> {

    private final RoleRepository roleRepository;

    public SearchRoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public ResponseEntity<List<Role>> execute(String name) {
        List<Role> roles = roleRepository.findByNameContaining(name);
        return ResponseEntity.ok(roles);
    }
}
