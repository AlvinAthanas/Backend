package com.example.cms_backend.services.DioceseServices;

import com.example.cms_backend.abstractions.Command;
import com.example.cms_backend.exceptions.DioceseNotFoundException;
import com.example.cms_backend.model.Entities.Diocese;
import com.example.cms_backend.repositories.DioceseRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeleteDioceseService implements Command<Long,Void> {
    private final DioceseRepository dioceseRepository;

    public DeleteDioceseService(DioceseRepository dioceseRepository) {
        this.dioceseRepository = dioceseRepository;
    }

    @Override
    public ResponseEntity<Void> execute(Long id) {
        Optional<Diocese> dioceseOptional = this.dioceseRepository.findById(id);
        if (dioceseOptional.isPresent()) {
            dioceseRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        throw new DioceseNotFoundException();
    }
}
