package com.example.cms_backend.Services.ContributionServices;

import com.example.cms_backend.Repositories.ContributionRepository;
import org.springframework.stereotype.Service;

@Service
public class GetContributionsService {
    private final ContributionRepository contributionRepository;

    public GetContributionsService(ContributionRepository contributionRepository) {
        this.contributionRepository = contributionRepository;
    }
}
