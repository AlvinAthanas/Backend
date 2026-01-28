package com.example.cms_backend.services.DioceseServices;

import com.example.cms_backend.abstractions.Command;
import com.example.cms_backend.model.Entities.Diocese;
import com.example.cms_backend.repositories.DioceseRepository;
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
