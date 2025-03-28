package com.example.cms_backend.Services.DioceseServices;

import com.example.cms_backend.Abstractions.Command;
import com.example.cms_backend.Model.Entities.Diocese;
import com.example.cms_backend.Repositories.DioceseRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SearchDioceseService implements Command<String, List<Diocese>> {

    private final DioceseRepository dioceseRepository;

    public SearchDioceseService(DioceseRepository dioceseRepository) {
        this.dioceseRepository = dioceseRepository;
    }

    @Override
    public ResponseEntity<List<Diocese>> execute(String name) {
        return ResponseEntity.ok(dioceseRepository.findDioceseByNameContaining(name));
    }
}
