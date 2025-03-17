package com.example.cms_backend.Services.ParishServices;

import com.example.cms_backend.Repositories.ParishRepository;
import org.springframework.stereotype.Service;

@Service
public class GetParishesService {
    private final ParishRepository parishRepository;

    public GetParishesService(ParishRepository parishRepository) {
        this.parishRepository = parishRepository;
    }
}
