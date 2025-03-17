package com.example.cms_backend.Services.DioceseServices;

import com.example.cms_backend.Repositories.DioceseRepository;
import org.springframework.stereotype.Service;

@Service
public class UpdateDioceseService {
    private final DioceseRepository dioceseRepository;

    public UpdateDioceseService(DioceseRepository dioceseRepository) {
        this.dioceseRepository = dioceseRepository;
    }
}
