package com.example.cms_backend.Services.ContributionServices;

import com.example.cms_backend.Abstractions.Query;
import com.example.cms_backend.Exceptions.ContributionNotFoundException;
import com.example.cms_backend.Model.Entities.Contribution;
import com.example.cms_backend.Repositories.ContributionRepository;
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
