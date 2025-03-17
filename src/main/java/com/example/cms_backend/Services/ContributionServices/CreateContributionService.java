package com.example.cms_backend.Services.ContributionServices;

import com.example.cms_backend.Repositories.ContributionRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateContributionService {
    private final ContributionRepository contributionRepository;

    public CreateContributionService(ContributionRepository contributionRepository) {
        this.contributionRepository = contributionRepository;
    }
}
