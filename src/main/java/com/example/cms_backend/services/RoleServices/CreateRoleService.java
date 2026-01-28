package com.example.cms_backend.services.RoleServices;

import com.example.cms_backend.abstractions.Command;
import com.example.cms_backend.model.Entities.Role;
import com.example.cms_backend.repositories.RoleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CreateRoleService implements Command<Role,Role> {
    private final RoleRepository roleRepository;

    public CreateRoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    @Override
    public ResponseEntity<Role> execute(Role role) {
        roleRepository.save(role);
        return ResponseEntity.status(HttpStatus.CREATED).body(role);
    }
}
