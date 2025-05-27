package com.example.cms_backend.Services.KandaServices;

import com.example.cms_backend.Abstractions.Command;
import com.example.cms_backend.Model.Entities.Kanda;
import com.example.cms_backend.Repositories.KandaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreateManyKandaService implements Command<List<Kanda>, List<Kanda>> {
    private final KandaRepository kandaRepository;

    public CreateManyKandaService(KandaRepository kandaRepository) {
        this.kandaRepository = kandaRepository;
    }

    @Override
    public ResponseEntity<List<Kanda>> execute(List<Kanda> kandas) {
        List<Kanda> savedKandas = kandaRepository.saveAll(kandas);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedKandas);
    }
}

