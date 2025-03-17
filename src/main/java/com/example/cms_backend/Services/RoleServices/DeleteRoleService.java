package com.example.cms_backend.Services.RoleServices;

import com.example.cms_backend.Repositories.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class DeleteRoleService {
    private final RoleRepository roleRepository;

    public DeleteRoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
}
