package com.example.cms_backend.Services.ContributionServices;

import com.example.cms_backend.Abstractions.Command;
import com.example.cms_backend.Exceptions.ContributionNotFoundException;
import com.example.cms_backend.Model.Entities.Contribution;
import com.example.cms_backend.Repositories.ContributionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeleteContributionService implements Command<Long,Void> {
    private final ContributionRepository contributionRepository;

    public DeleteContributionService(ContributionRepository contributionRepository) {
        this.contributionRepository = contributionRepository;
    }

    @Override
    public ResponseEntity<Void> execute(Long id) {
        Optional<Contribution> contributionOptional = contributionRepository.findById(id);
        if (contributionOptional.isPresent()) {
            contributionRepository.delete(contributionOptional.get());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        throw new ContributionNotFoundException();
    }
}
