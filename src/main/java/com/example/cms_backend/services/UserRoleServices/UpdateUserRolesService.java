package com.example.cms_backend.services.UserRoleServices;

import com.example.cms_backend.abstractions.Command;
import com.example.cms_backend.exceptions.RoleNotFoundException;
import com.example.cms_backend.exceptions.UserNotFoundException;
import com.example.cms_backend.model.Entities.Role;
import com.example.cms_backend.model.Entities.User;
import com.example.cms_backend.model.Commands.UpdateUserRolesCommand;
import com.example.cms_backend.repositories.RoleRepository;
import com.example.cms_backend.repositories.UserRepository;
import com.example.cms_backend.services.UserAuthorityServices.RoleBasedAuthorityService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UpdateUserRolesService implements Command<UpdateUserRolesCommand, String> {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final RoleBasedAuthorityService roleBasedAuthorityService;

    public UpdateUserRolesService(UserRepository userRepository, 
                                 RoleRepository roleRepository,
                                 RoleBasedAuthorityService roleBasedAuthorityService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.roleBasedAuthorityService = roleBasedAuthorityService;
    }

    @Override
    public ResponseEntity<String> execute(UpdateUserRolesCommand command) {
        Optional<User> userOptional = userRepository.findById(command.getId());
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException();
        }

        User user = userOptional.get();
        Set<Role> newRoles = new HashSet<>();

        for (String roleName : command.getRoleNames()) {
            Role role = roleRepository.findByName(roleName)
                    .orElseThrow(() -> new RoleNotFoundException());
            newRoles.add(role);
        }

        user.setRoles(newRoles); // 💥 Completely replace old roles
        userRepository.save(user);

        // Update authorities based on the new roles
        roleBasedAuthorityService.updateAuthoritiesBasedOnRoles(user);

        return ResponseEntity.ok("Roles updated successfully!");
    }
}
