package com.example.cms_backend.services.ContributionServices;

import com.example.cms_backend.abstractions.Query;
import com.example.cms_backend.exceptions.ContributionNotFoundException;
import com.example.cms_backend.model.Entities.Contribution;
import com.example.cms_backend.repositories.ContributionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GetContributionService implements Query<Long, Contribution> {
    private final ContributionRepository contributionRepository;

    public GetContributionService(ContributionRepository contributionRepository) {
        this.contributionRepository = contributionRepository;
    }

    @Override
    public ResponseEntity<Contribution> execute(Long id) {
        Optional<Contribution> contributionOptional = contributionRepository.findById(id);
        if (contributionOptional.isPresent()) {
            return ResponseEntity.ok(contributionOptional.get());
        }
        throw new ContributionNotFoundException();
    }
}
