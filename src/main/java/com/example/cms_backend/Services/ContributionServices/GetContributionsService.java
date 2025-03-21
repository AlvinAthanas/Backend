package com.example.cms_backend.Services.ContributionServices;

import com.example.cms_backend.Abstractions.Query;
import com.example.cms_backend.Model.Entities.Contribution;
import com.example.cms_backend.Repositories.ContributionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetContributionsService implements Query<Void, List<Contribution>> {
    private final ContributionRepository contributionRepository;

    public GetContributionsService(ContributionRepository contributionRepository) {
        this.contributionRepository = contributionRepository;
    }

    @Override
    public ResponseEntity<List<Contribution>> execute(Void input) {
        List<Contribution> contributions = contributionRepository.findAll();
        return ResponseEntity.ok(contributions);
    }
}
