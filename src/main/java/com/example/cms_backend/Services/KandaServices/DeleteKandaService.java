package com.example.cms_backend.Services.KandaServices;

import com.example.cms_backend.Abstractions.Command;
import com.example.cms_backend.Exceptions.KandaNotFoundException;
import com.example.cms_backend.Model.Entities.Kanda;
import com.example.cms_backend.Repositories.KandaRepository;
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

