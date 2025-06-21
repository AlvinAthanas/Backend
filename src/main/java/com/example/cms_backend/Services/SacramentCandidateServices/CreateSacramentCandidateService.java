package com.example.cms_backend.Services.SacramentCandidateServices;

import com.example.cms_backend.Abstractions.*;
import com.example.cms_backend.Model.Commands.CreateSacramentCandidateCommand;
import com.example.cms_backend.Model.Entities.SacramentCandidate;
import com.example.cms_backend.Repositories.SacramentCandidateRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

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