package com.example.cms_backend.services.DioceseServices;

import com.example.cms_backend.abstractions.Command;
import com.example.cms_backend.model.Entities.Diocese;
import com.example.cms_backend.repositories.DioceseRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CreateDiocesesService implements Command<List<Diocese>,List<Diocese>> {
    private final DioceseRepository dioceseRepository;

    public CreateDiocesesService(DioceseRepository dioceseRepository) {
        this.dioceseRepository = dioceseRepository;
    }

    @Override
    public ResponseEntity<List<Diocese>> execute(List<Diocese> dioceses) {
        dioceseRepository.saveAll(dioceses);
        return ResponseEntity.status(HttpStatus.CREATED).body(dioceses);
    }
}
