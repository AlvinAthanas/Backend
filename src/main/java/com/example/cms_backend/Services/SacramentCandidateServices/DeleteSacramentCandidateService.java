package com.example.cms_backend.Services.SacramentCandidateServices;

import com.example.cms_backend.Abstractions.Command;
import com.example.cms_backend.Repositories.SacramentCandidateRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class DeleteSacramentCandidateService implements Command<Long, Void> {
    private final SacramentCandidateRepository repository;

    public DeleteSacramentCandidateService(SacramentCandidateRepository repository) {
        this.repository = repository;
    }

    @Override
    public ResponseEntity<Void> execute(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
