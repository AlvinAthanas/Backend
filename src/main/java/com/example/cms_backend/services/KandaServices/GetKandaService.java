package com.example.cms_backend.services.KandaServices;

import com.example.cms_backend.abstractions.Query;
import com.example.cms_backend.exceptions.KandaNotFoundException;
import com.example.cms_backend.model.Entities.Kanda;
import com.example.cms_backend.repositories.KandaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GetKandaService implements Query<Long, Kanda> {
    private final KandaRepository kandaRepository;

    public GetKandaService(KandaRepository kandaRepository) {
        this.kandaRepository = kandaRepository;
    }

    @Override
    public ResponseEntity<Kanda> execute(Long id) {
        Optional<Kanda> kandaOptional = kandaRepository.findById(id);
        if (kandaOptional.isPresent()) {
            return ResponseEntity.ok(kandaOptional.get());
        }
        throw new KandaNotFoundException();
    }
}

