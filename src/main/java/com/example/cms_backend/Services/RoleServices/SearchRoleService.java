package com.example.cms_backend.Services.RoleServices;

import com.example.cms_backend.Abstractions.Query;
import com.example.cms_backend.Model.Entities.Role;
import com.example.cms_backend.Repositories.RoleRepository;
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
