package com.example.cms_backend.Services.ParishServices;

import com.example.cms_backend.Abstractions.Query;
import com.example.cms_backend.Model.Entities.Parish;
import com.example.cms_backend.Repositories.ParishRepository;
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
