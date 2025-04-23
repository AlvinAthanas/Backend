package com.example.cms_backend.Services.ContributionServices;

import com.example.cms_backend.Abstractions.Query;
import com.example.cms_backend.Model.DTO.ContributionFilterDTO;
import com.example.cms_backend.Model.Entities.Contribution;
import com.example.cms_backend.Repositories.ContributionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetFilteredContributionsService implements Query<ContributionFilterDTO, List<Contribution>> {

    private final ContributionRepository contributionRepository;

    public GetFilteredContributionsService(ContributionRepository contributionRepository) {
        this.contributionRepository = contributionRepository;
    }

    @Override
    public ResponseEntity<List<Contribution>> execute(ContributionFilterDTO input) {
        List<Contribution> results = contributionRepository.filterByTypeMonthYear(
                input.getType() != null && !input.getType().isEmpty() ? input.getType() : null,
                input.getMonth(),
                input.getYear()
        );
        return ResponseEntity.ok(results);
    }
}
