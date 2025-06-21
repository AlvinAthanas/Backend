package com.example.cms_backend.Services.SacramentCandidateServices;

import com.example.cms_backend.Abstractions.Query;
import com.example.cms_backend.Model.Entities.SacramentCandidate;
import com.example.cms_backend.Repositories.SacramentCandidateRepository;
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
