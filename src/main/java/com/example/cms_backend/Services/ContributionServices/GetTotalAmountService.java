package com.example.cms_backend.Services.ContributionServices;

import com.example.cms_backend.Abstractions.Query;
import com.example.cms_backend.Repositories.ContributionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class GetTotalAmountService implements Query<Void,Long> {
    private final ContributionRepository contributionRepository;

    public GetTotalAmountService(ContributionRepository contributionRepository) {
        this.contributionRepository = contributionRepository;
    }

    @Override
    public ResponseEntity<Long> execute(Void input) {
        return ResponseEntity.ok(contributionRepository.getTotalAmount());
    }
}
