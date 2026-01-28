package com.example.cms_backend.services.DioceseServices;

import com.example.cms_backend.abstractions.Command;
import com.example.cms_backend.model.Entities.Diocese;
import com.example.cms_backend.repositories.DioceseRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CreateDioceseService implements Command<Diocese,Diocese> {
    private final DioceseRepository dioceseRepository;

    public CreateDioceseService(DioceseRepository dioceseRepository) {
        this.dioceseRepository = dioceseRepository;
    }

    @Override
    public ResponseEntity<Diocese> execute(Diocese diocese) {
        dioceseRepository.save(diocese);
        return ResponseEntity.status(HttpStatus.CREATED).body(diocese);
    }
}
