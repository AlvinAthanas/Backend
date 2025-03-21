package com.example.cms_backend.Services.ContributionServices;

import com.example.cms_backend.Abstractions.Command;
import com.example.cms_backend.Exceptions.ContributionNotFoundException;
import com.example.cms_backend.Model.Entities.Contribution;
import com.example.cms_backend.Model.UpdateCommands.UpdateContributionCommand;
import com.example.cms_backend.Repositories.ContributionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UpdateContributionService implements Command<UpdateContributionCommand, Contribution> {
    private final ContributionRepository contributionRepository;

    public UpdateContributionService(ContributionRepository contributionRepository) {
        this.contributionRepository = contributionRepository;
    }

    @Override
    public ResponseEntity<Contribution> execute(UpdateContributionCommand command) {
        Optional<Contribution> contributionOptional = contributionRepository.findById(command.getId());
        if (contributionOptional.isPresent()) {
            Contribution contribution = command.getContribution();
            contribution.setId(command.getId());
            contributionRepository.save(contribution);
            return ResponseEntity.ok(contribution);
        }
        throw new ContributionNotFoundException();
    }
}
