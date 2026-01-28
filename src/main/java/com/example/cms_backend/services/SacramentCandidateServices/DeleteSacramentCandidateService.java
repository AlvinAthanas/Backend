package com.example.cms_backend.services.SacramentCandidateServices;

import com.example.cms_backend.abstractions.Command;
import com.example.cms_backend.repositories.SacramentCandidateRepository;
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
