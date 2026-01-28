package com.example.cms_backend.services.SacramentCandidateServices;

import com.example.cms_backend.abstractions.Query;
import com.example.cms_backend.model.Entities.SacramentCandidate;
import com.example.cms_backend.repositories.SacramentCandidateRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetSacramentCandidatesService implements Query<Void, List<SacramentCandidate>> {
    private final SacramentCandidateRepository repository;

    public GetSacramentCandidatesService(SacramentCandidateRepository repository) {
        this.repository = repository;
    }

    @Override
    public ResponseEntity<List<SacramentCandidate>> execute(Void input) {
        return ResponseEntity.ok(repository.findAll());
    }
}
