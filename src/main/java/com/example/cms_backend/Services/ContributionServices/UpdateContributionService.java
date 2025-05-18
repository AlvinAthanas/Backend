package com.example.cms_backend.Services.ContributionServices;

import com.example.cms_backend.Abstractions.Command;
import com.example.cms_backend.Exceptions.ContributionNotFoundException;
import com.example.cms_backend.Model.Entities.Contribution;
import com.example.cms_backend.Model.Commands.UpdateContributionCommand;
import com.example.cms_backend.Model.Entities.User;
import com.example.cms_backend.Repositories.ContributionRepository;
import com.example.cms_backend.Repositories.UserRepository;
import com.example.cms_backend.Utils.LoggedInUserUtil;
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
                    contribution.setUserId(user.getId());
                    contribution.setParishId(user.getParishId());
                }
            }

            contributionRepository.save(contribution);
            return ResponseEntity.ok(contribution);
        }
        throw new ContributionNotFoundException();
    }
}
