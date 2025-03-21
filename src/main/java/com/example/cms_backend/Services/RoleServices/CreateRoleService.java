package com.example.cms_backend.Services.RoleServices;

import com.example.cms_backend.Abstractions.Command;
import com.example.cms_backend.Model.Entities.Role;
import com.example.cms_backend.Repositories.RoleRepository;
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
