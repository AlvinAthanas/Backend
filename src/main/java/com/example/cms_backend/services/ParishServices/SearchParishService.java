package com.example.cms_backend.services.ParishServices;

import com.example.cms_backend.abstractions.Query;
import com.example.cms_backend.model.Entities.Parish;
import com.example.cms_backend.repositories.ParishRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchParishService implements Query<String, List<Parish>> {

    private final ParishRepository parishRepository;

    public SearchParishService(ParishRepository parishRepository) {
        this.parishRepository = parishRepository;
    }


    @Override
    public ResponseEntity<List<Parish>> execute(String name) {
        List<Parish> parishes = parishRepository.findByNameContaining(name);
        return ResponseEntity.ok(parishes);
    }
}
