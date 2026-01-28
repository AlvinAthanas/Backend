package com.example.cms_backend.services.RoleServices;

import com.example.cms_backend.abstractions.Command;
import com.example.cms_backend.exceptions.RoleNotFoundException;
import com.example.cms_backend.model.Entities.Role;
import com.example.cms_backend.repositories.RoleRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeleteRoleService implements Command<Long,Void> {
    private final RoleRepository roleRepository;

    public DeleteRoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public ResponseEntity<Void> execute(Long id) {
        Optional<Role> roleOptional = roleRepository.findById(id);
        if (roleOptional.isPresent()) {
            roleRepository.deleteById(id);
        }
        throw new RoleNotFoundException();
    }
}
