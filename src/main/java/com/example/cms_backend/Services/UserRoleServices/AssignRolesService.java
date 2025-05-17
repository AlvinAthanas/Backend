package com.example.cms_backend.Services.UserRoleServices;

import com.example.cms_backend.Abstractions.Command;
import com.example.cms_backend.Exceptions.RoleNotFoundException;
import com.example.cms_backend.Exceptions.UserNotFoundException;
import com.example.cms_backend.Model.Entities.Role;
import com.example.cms_backend.Model.Entities.User;
import com.example.cms_backend.Model.Enums.Roles;
import com.example.cms_backend.Model.Commands.AssignRoleCommand;
import com.example.cms_backend.Repositories.RoleRepository;
import com.example.cms_backend.Repositories.UserRepository;
import com.example.cms_backend.Services.UserAuthorityServices.RoleBasedAuthorityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
@Service
public class AssignRolesService implements Command<AssignRoleCommand,String> {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final RoleBasedAuthorityService roleBasedAuthorityService;


    public AssignRolesService(RoleRepository roleRepository,
                              UserRepository userRepository,
                              RoleBasedAuthorityService roleBasedAuthorityService) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.roleBasedAuthorityService = roleBasedAuthorityService;
    }

    public void AssignDefaultRole(User user) {
        Optional<Role> defaultRole = roleRepository.findByName(Roles.PARISH_MEMBER.toString());
        if (defaultRole.isPresent()) {
            if (user.getRoles() == null) {
                user.setRoles(new HashSet<>());
            }
            user.getRoles().add(defaultRole.get());

            Optional<Role> commiteeChairperson = roleRepository.findByName(Roles.COMMITTEE_CHAIRPERSON.toString());
            if (commiteeChairperson.isPresent()) {
                user.getRoles().add(commiteeChairperson.get());
            }

            // Update authorities based on the assigned roles
            roleBasedAuthorityService.updateAuthoritiesBasedOnRoles(user);
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

                // Update authorities based on the assigned roles
                roleBasedAuthorityService.updateAuthoritiesBasedOnRoles(user);

                return ResponseEntity.status(HttpStatus.CREATED).body("Role assigned: " + command.getRoleName());
            } else {
                throw new UserNotFoundException();
            }

        } else {
            throw new RoleNotFoundException();
        }
    }
}
