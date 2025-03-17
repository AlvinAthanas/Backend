package com.example.cms_backend.Services.RoleServices;

import com.example.cms_backend.Repositories.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class UpdateRoleService {
    private final RoleRepository roleRepository;

    public UpdateRoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
}
