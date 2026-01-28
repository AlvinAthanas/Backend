package com.example.cms_backend.services.RoleServices;

import com.example.cms_backend.abstractions.Query;
import com.example.cms_backend.exceptions.RoleNotFoundException;
import com.example.cms_backend.model.Entities.Role;
import com.example.cms_backend.repositories.RoleRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GetRoleService implements Query<Long, Role> {
    private final RoleRepository roleRepository;

    public GetRoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public ResponseEntity<Role> execute(Long id) {
        Optional<Role> roleOptional = roleRepository.findById(id);
        if (roleOptional.isPresent()) {
            return ResponseEntity.ok(roleOptional.get());
        }
        throw new RoleNotFoundException();
    }
}
