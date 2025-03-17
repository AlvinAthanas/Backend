package com.example.cms_backend.Services.ParishServices;

import com.example.cms_backend.Repositories.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateParishService {
    private final RoleRepository roleRepository;

    public CreateParishService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
}
