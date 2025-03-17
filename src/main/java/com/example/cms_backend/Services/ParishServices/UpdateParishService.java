package com.example.cms_backend.Services.ParishServices;

import com.example.cms_backend.Repositories.ParishRepository;
import com.example.cms_backend.Repositories.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class UpdateParishService {
    private final ParishRepository parishRepository;

    public UpdateParishService(ParishRepository parishRepository) {
        this.parishRepository = parishRepository;
    }
}
