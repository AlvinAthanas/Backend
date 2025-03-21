package com.example.cms_backend.Services.DioceseServices;

import com.example.cms_backend.Abstractions.Query;
import com.example.cms_backend.Model.Entities.Diocese;
import com.example.cms_backend.Repositories.DioceseRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetDiocesesService implements Query<Void, List<Diocese>> {
    private final DioceseRepository dioceseRepository;

    public GetDiocesesService(DioceseRepository dioceseRepository) {
        this.dioceseRepository = dioceseRepository;
    }

    @Override
    public ResponseEntity<List<Diocese>> execute(Void input) {
        List<Diocese> dioceses = dioceseRepository.findAll();
        return ResponseEntity.ok().body(dioceses);
    }
}
