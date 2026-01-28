package com.example.cms_backend.services.SacramentCandidateServices;

import com.example.cms_backend.abstractions.Query;
import com.example.cms_backend.model.Entities.SacramentCandidate;
import com.example.cms_backend.repositories.SacramentCandidateRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class GetSacramentCandidateService implements Query<Long, SacramentCandidate> {
    private final SacramentCandidateRepository repository;

    public GetSacramentCandidateService(SacramentCandidateRepository repository) {
        this.repository = repository;
    }

    @Override
    public ResponseEntity<SacramentCandidate> execute(Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
