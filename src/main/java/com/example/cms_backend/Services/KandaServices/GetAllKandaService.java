package com.example.cms_backend.Services.KandaServices;

import com.example.cms_backend.Abstractions.Query;
import com.example.cms_backend.Model.Entities.Kanda;
import com.example.cms_backend.Repositories.KandaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllKandaService implements Query<Void, List<Kanda>> {
    private final KandaRepository kandaRepository;

    public GetAllKandaService(KandaRepository kandaRepository) {
        this.kandaRepository = kandaRepository;
    }

    @Override
    public ResponseEntity<List<Kanda>> execute(Void input) {
        List<Kanda> kandas = kandaRepository.findAll();
        return ResponseEntity.ok(kandas);
    }
}

