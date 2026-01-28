package com.example.cms_backend.services.ParishServices;

import com.example.cms_backend.abstractions.Command;
import com.example.cms_backend.model.Entities.Parish;
import com.example.cms_backend.repositories.ParishRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreateParishesService implements Command<List<Parish>,List<Parish>> {
    private final ParishRepository parishRepository;

    public CreateParishesService(ParishRepository parishRepository) {
        this.parishRepository = parishRepository;
    }

    @Override
    public ResponseEntity<List<Parish>> execute(List<Parish> parishes) {
        parishRepository.saveAll(parishes);
        return ResponseEntity.status(HttpStatus.CREATED).body(parishes);
    }
}
