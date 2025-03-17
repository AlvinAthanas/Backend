package com.example.cms_backend.Services.RoleServices;

import com.example.cms_backend.Repositories.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class GetRoleService {
    private final RoleRepository roleRepository;

    public GetRoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
}
