package com.example.cms_backend.Services.ParishServices;

import com.example.cms_backend.Repositories.ParishRepository;
import org.springframework.stereotype.Service;

@Service
public class DeleteParishService {
    private final ParishRepository parishRepository;

    public DeleteParishService(ParishRepository parishRepository) {
        this.parishRepository = parishRepository;
    }
}
