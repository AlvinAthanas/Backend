package com.example.cms_backend.services.ContributionServices;

import com.example.cms_backend.abstractions.Command;
import com.example.cms_backend.exceptions.ContributionNotFoundException;
import com.example.cms_backend.model.Entities.Contribution;
import com.example.cms_backend.model.Commands.UpdateContributionCommand;
import com.example.cms_backend.model.Entities.User;
import com.example.cms_backend.repositories.ContributionRepository;
import com.example.cms_backend.repositories.UserRepository;
import com.example.cms_backend.utils.LoggedInUserUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UpdateContributionService implements Command<UpdateContributionCommand, Contribution> {
    private final ContributionRepository contributionRepository;
    private final UserRepository userRepository;

    public UpdateContributionService(ContributionRepository contributionRepository, UserRepository userRepository) {
        this.contributionRepository = contributionRepository;
        this.userRepository = userRepository;
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

    public ResponseEntity<Contribution> execute(UpdateContributionCommand command, HttpServletRequest request) {
        Optional<Contribution> contributionOptional = contributionRepository.findById(command.getId());
        if (contributionOptional.isPresent()) {
            Contribution contribution = command.getContribution();
            contribution.setId(command.getId());

            // Get the logged-in user's email
            String email = LoggedInUserUtil.loggedInUserEmail(request);
            if (email != null) {
                Optional<User> userOptional = userRepository.findByEmail(email);
                if (userOptional.isPresent()) {
                    User user = userOptional.get();
                    contribution.setRecorderId(user.getId());
                    contribution.setParishId(user.getParishId());
                }
            }

            contributionRepository.save(contribution);
            return ResponseEntity.ok(contribution);
        }
        throw new ContributionNotFoundException();
    }
}
