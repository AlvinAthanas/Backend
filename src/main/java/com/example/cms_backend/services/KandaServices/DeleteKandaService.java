package com.example.cms_backend.services.KandaServices;

import com.example.cms_backend.abstractions.Command;
import com.example.cms_backend.exceptions.KandaNotFoundException;
import com.example.cms_backend.model.Entities.Kanda;
import com.example.cms_backend.repositories.KandaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeleteKandaService implements Command<Long, Void> {
    private final KandaRepository kandaRepository;

    public DeleteKandaService(KandaRepository kandaRepository) {
        this.kandaRepository = kandaRepository;
    }

    @Override
    public ResponseEntity<Void> execute(Long id) {
        Optional<Kanda> kandaOptional = kandaRepository.findById(id);
        if (kandaOptional.isPresent()) {
            kandaRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        throw new KandaNotFoundException();
    }
}

