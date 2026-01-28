package com.example.cms_backend.services.ParishServices;

import com.example.cms_backend.abstractions.Query;
import com.example.cms_backend.model.Entities.Parish;
import com.example.cms_backend.repositories.ParishRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetParishesService implements Query<Void, List<Parish>> {
    private final ParishRepository parishRepository;

    public GetParishesService(ParishRepository parishRepository) {
        this.parishRepository = parishRepository;
    }

    @Override
    @Cacheable("parishesCache")
    public ResponseEntity<List<Parish>> execute(Void input) {
        List<Parish> parishes = parishRepository.findAll();
        return ResponseEntity.ok(parishes);
    }
}
