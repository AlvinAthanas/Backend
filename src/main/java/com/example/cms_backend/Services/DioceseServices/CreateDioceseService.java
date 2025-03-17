package com.example.cms_backend.Services.DioceseServices;

import com.example.cms_backend.Repositories.DioceseRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateDioceseService {
    private final DioceseRepository dioceseRepository;

    public CreateDioceseService(DioceseRepository dioceseRepository) {
        this.dioceseRepository = dioceseRepository;
    }
}
