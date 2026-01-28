package com.example.cms_backend.services.SacramentCandidateServices;

import com.example.cms_backend.abstractions.Command;
import com.example.cms_backend.model.Commands.UpdateSacramentCandidateCommand;
import com.example.cms_backend.model.Entities.SacramentCandidate;
import com.example.cms_backend.repositories.SacramentCandidateRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UpdateSacramentCandidateService implements Command<UpdateSacramentCandidateCommand, SacramentCandidate> {
    private final SacramentCandidateRepository repository;

    public UpdateSacramentCandidateService(SacramentCandidateRepository repository) {
        this.repository = repository;
    }

    @Override
    public ResponseEntity<SacramentCandidate> execute(UpdateSacramentCandidateCommand cmd) {
        return repository.findById(cmd.getId())
                .map(existing -> {
                    existing.setFullName(cmd.getFullName());
                    existing.setGender(cmd.getGender());
                    existing.setContactInfo(cmd.getPhoneNumber());
                    existing.setGuardianName(cmd.getGuardianName());
                    return ResponseEntity.ok(repository.save(existing));
                }).orElse(ResponseEntity.notFound().build());
    }
}

