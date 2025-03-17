package com.example.cms_backend.Services.RoleServices;

import com.example.cms_backend.Repositories.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class GetRolesService {
    private final RoleRepository roleRepository;

    public GetRolesService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
}
