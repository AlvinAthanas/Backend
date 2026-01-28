package com.example.cms_backend.services.RoleServices;

import com.example.cms_backend.abstractions.Command;
import com.example.cms_backend.exceptions.RoleNotFoundException;
import com.example.cms_backend.model.Entities.Role;
import com.example.cms_backend.model.Commands.UpdateRoleCommand;
import com.example.cms_backend.repositories.RoleRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UpdateRoleService implements Command<UpdateRoleCommand, Role> {
    private final RoleRepository roleRepository;

    public UpdateRoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    @Override
    public ResponseEntity<Role> execute(UpdateRoleCommand command) {
        Optional<Role> roleOptional = roleRepository.findById(command.getId());
        if (roleOptional.isPresent()) {
            Role role = command.getRole();
            role.setId(command.getId());
            roleRepository.save(role);
            return ResponseEntity.ok(role);
        }
        throw new RoleNotFoundException();
    }
}
