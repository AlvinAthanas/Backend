package com.example.cms_backend.Roles_Authorities;

import com.example.cms_backend.Abstractions.Command;
import com.example.cms_backend.Exceptions.RoleNotFoundException;
import com.example.cms_backend.Exceptions.UserNotFoundException;
import com.example.cms_backend.Model.Entities.Role;
import com.example.cms_backend.Model.Entities.User;
import com.example.cms_backend.Model.Enums.Roles;
import com.example.cms_backend.Model.UpdateCommands.AssignRoleCommand;
import com.example.cms_backend.Repositories.RoleRepository;
import com.example.cms_backend.Repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
@Service
public class UserRoles implements Command<AssignRoleCommand,String> {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;


    public UserRoles(RoleRepository roleRepository,
                     UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    public void AssignDefaultRole(User user) {
        Optional<Role> defaultRole = roleRepository.findByName(Roles.PARISH_MEMBER.toString());
        if (defaultRole.isPresent()) {
            if (user.getRoles() == null) {
                user.setRoles(new HashSet<>());
            }
            user.getRoles().add(defaultRole.get());
        }
    }


    @Override
    public ResponseEntity<String> execute(AssignRoleCommand command) {
        Optional<Role> assignedRole = roleRepository.findByName(command.getRoleName());
        Optional<User> userOptional = userRepository.findById(command.getId());
        if (assignedRole.isPresent()) {
            if (userOptional.isPresent()) {
                Role role = assignedRole.get();
                User user = userOptional.get();
                if (user.getRoles() == null) {
                    user.setRoles(new HashSet<>());
                }
                user.getRoles().add(role);
                userRepository.save(user);
                return ResponseEntity.status(HttpStatus.CREATED).body("Role assigned: " + command.getRoleName());
            } else {
                throw new UserNotFoundException();
            }

        } else {
            throw new RoleNotFoundException();
        }
    }
}
