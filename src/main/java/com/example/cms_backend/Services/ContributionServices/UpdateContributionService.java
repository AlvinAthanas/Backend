package com.example.cms_backend.Services.ContributionServices;

import com.example.cms_backend.Repositories.ContributionRepository;
import org.springframework.stereotype.Service;

@Service
public class UpdateContributionService {
    private final ContributionRepository contributionRepository;

    public UpdateContributionService(ContributionRepository contributionRepository) {
        this.contributionRepository = contributionRepository;
    }
}
