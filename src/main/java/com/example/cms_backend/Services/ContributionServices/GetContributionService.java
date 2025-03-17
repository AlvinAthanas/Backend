package com.example.cms_backend.Services.ContributionServices;

import com.example.cms_backend.Repositories.ContributionRepository;
import org.springframework.stereotype.Service;

@Service
public class GetContributionService {
    private final ContributionRepository contributionRepository;

    public GetContributionService(ContributionRepository contributionRepository) {
        this.contributionRepository = contributionRepository;
    }
}
