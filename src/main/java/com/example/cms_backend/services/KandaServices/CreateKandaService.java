package com.example.cms_backend.services.KandaServices;

import com.example.cms_backend.abstractions.Command;
import com.example.cms_backend.model.Entities.Kanda;
import com.example.cms_backend.repositories.KandaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CreateKandaService implements Command<Kanda, Kanda> {
    private final KandaRepository kandaRepository;

    public CreateKandaService(KandaRepository kandaRepository) {
        this.kandaRepository = kandaRepository;
    }

    @Override
    public ResponseEntity<Kanda> execute(Kanda kanda) {
        kandaRepository.save(kanda);
        return ResponseEntity.status(HttpStatus.CREATED).body(kanda);
    }
}

