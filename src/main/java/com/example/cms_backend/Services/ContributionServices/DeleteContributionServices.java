package com.example.cms_backend.Services.ContributionServices;

import com.example.cms_backend.Repositories.ContributionRepository;
import org.springframework.stereotype.Service;

@Service
public class DeleteContributionServices {
    private final ContributionRepository contributionRepository;

    public DeleteContributionServices(ContributionRepository contributionRepository) {
        this.contributionRepository = contributionRepository;
    }
}
