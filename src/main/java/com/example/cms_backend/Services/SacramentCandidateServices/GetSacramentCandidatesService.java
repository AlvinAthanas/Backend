package com.example.cms_backend.Services.SacramentCandidateServices;

import com.example.cms_backend.Abstractions.Query;
import com.example.cms_backend.Model.Entities.SacramentCandidate;
import com.example.cms_backend.Repositories.SacramentCandidateRepository;
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
