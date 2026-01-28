package com.example.cms_backend.services.SacramentCandidateServices;

import com.example.cms_backend.abstractions.*;
import com.example.cms_backend.model.Commands.CreateSacramentCandidateCommand;
import com.example.cms_backend.model.Entities.SacramentCandidate;
import com.example.cms_backend.repositories.SacramentCandidateRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CreateSacramentCandidateService implements Command<CreateSacramentCandidateCommand, SacramentCandidate> {
    private final SacramentCandidateRepository repository;

    public CreateSacramentCandidateService(SacramentCandidateRepository repository) {
        this.repository = repository;
    }

    @Override
    public ResponseEntity<SacramentCandidate> execute(CreateSacramentCandidateCommand cmd) {
        SacramentCandidate c = new SacramentCandidate();
        c.setFullName(cmd.getFullName());
        c.setGender(cmd.getGender());
        c.setContactInfo(cmd.getPhoneNumber());
        c.setGuardianName(cmd.getGuardianName());
        if (cmd.getUserId() != null) {
            c.setHasAccount(true);
            c.setUserId(cmd.getUserId());
        }
        return ResponseEntity.ok(repository.save(c));
    }
}