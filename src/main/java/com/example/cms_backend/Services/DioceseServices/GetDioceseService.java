package com.example.cms_backend.Services.DioceseServices;

import com.example.cms_backend.Abstractions.Query;
import com.example.cms_backend.Exceptions.DioceseNotFoundException;
import com.example.cms_backend.Model.Entities.Diocese;
import com.example.cms_backend.Repositories.DioceseRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GetDioceseService implements Query<Long, Diocese> {
    private final DioceseRepository dioceseRepository;

    public GetDioceseService(DioceseRepository dioceseRepository) {
        this.dioceseRepository = dioceseRepository;
    }

    @Override
    public ResponseEntity<Diocese> execute(Long id) {
        Optional<Diocese> dioceseOptional = dioceseRepository.findById(id);
        if (dioceseOptional.isPresent()) {
            return ResponseEntity.ok().body(dioceseOptional.get());
        }
        throw new DioceseNotFoundException();
    }
}
