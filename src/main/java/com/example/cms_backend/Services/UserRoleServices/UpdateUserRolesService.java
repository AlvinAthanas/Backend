package com.example.cms_backend.Services.UserRoleServices;

import com.example.cms_backend.Abstractions.Command;
import com.example.cms_backend.Exceptions.RoleNotFoundException;
import com.example.cms_backend.Exceptions.UserNotFoundException;
import com.example.cms_backend.Model.Entities.Role;
import com.example.cms_backend.Model.Entities.User;
import com.example.cms_backend.Model.UpdateCommands.UpdateUserRolesCommand;
import com.example.cms_backend.Repositories.RoleRepository;
import com.example.cms_backend.Repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UpdateUserRolesService implements Command<UpdateUserRolesCommand, String> {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UpdateUserRolesService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
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

        return ResponseEntity.ok("Roles updated successfully!");
    }
}

