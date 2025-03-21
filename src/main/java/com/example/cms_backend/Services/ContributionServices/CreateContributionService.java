package com.example.cms_backend.Services.ContributionServices;

import com.example.cms_backend.Abstractions.Command;
import com.example.cms_backend.Model.Entities.Contribution;
import com.example.cms_backend.Repositories.ContributionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CreateContributionService implements Command<Contribution,Contribution> {
    private final ContributionRepository contributionRepository;

    public CreateContributionService(ContributionRepository contributionRepository) {
        this.contributionRepository = contributionRepository;
    }

    @Override
    public ResponseEntity<Contribution> execute(Contribution contribution) {
        contributionRepository.save(contribution);
        return ResponseEntity.status(HttpStatus.CREATED).body(contribution);
    }
}
