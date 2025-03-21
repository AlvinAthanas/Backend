package com.example.cms_backend.Services.ParishServices;

import com.example.cms_backend.Abstractions.Query;
import com.example.cms_backend.Exceptions.ParishNotFoundException;
import com.example.cms_backend.Model.Entities.Parish;
import com.example.cms_backend.Repositories.ParishRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GetParishService implements Query<Long, Parish> {
    private final ParishRepository parishRepository;

    public GetParishService(ParishRepository parishRepository) {
        this.parishRepository = parishRepository;
    }


    @Override
    public ResponseEntity<Parish> execute(Long id) {
        Optional<Parish> parishOptional = parishRepository.findById(id);
        if (parishOptional.isPresent()) {
            return ResponseEntity.ok(parishOptional.get());
        }
        throw new ParishNotFoundException();
    }
}
