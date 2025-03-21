package com.example.cms_backend.Services.RoleServices;

import com.example.cms_backend.Abstractions.Query;
import com.example.cms_backend.Exceptions.RoleNotFoundException;
import com.example.cms_backend.Model.Entities.Role;
import com.example.cms_backend.Repositories.RoleRepository;
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
