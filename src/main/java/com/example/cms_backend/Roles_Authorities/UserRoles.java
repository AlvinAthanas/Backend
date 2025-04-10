package com.example.cms_backend.Roles_Authorities;

import com.example.cms_backend.Model.Entities.Role;
import com.example.cms_backend.Model.Entities.User;
import com.example.cms_backend.Model.Enums.Roles;
import com.example.cms_backend.Repositories.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
@Service
public class UserRoles {
    private final RoleRepository roleRepository;


    public UserRoles(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
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
}
