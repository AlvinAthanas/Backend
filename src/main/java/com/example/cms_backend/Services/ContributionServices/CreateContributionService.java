package com.example.cms_backend.Services.ContributionServices;

import com.example.cms_backend.Abstractions.Command;
import com.example.cms_backend.Model.Entities.Contribution;
import com.example.cms_backend.Model.Entities.User;
import com.example.cms_backend.Repositories.ContributionRepository;
import com.example.cms_backend.Repositories.UserRepository;
import com.example.cms_backend.Utils.LoggedInUserUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CreateContributionService implements Command<Contribution,Contribution> {
    private final ContributionRepository contributionRepository;
    private final UserRepository userRepository;

    public CreateContributionService(ContributionRepository contributionRepository, UserRepository userRepository) {
        this.contributionRepository = contributionRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<Contribution> execute(Contribution contribution) {
        return ResponseEntity.status(HttpStatus.CREATED).body(contribution);
    }

    public ResponseEntity<Contribution> execute(Contribution contribution, HttpServletRequest request) {
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
        return ResponseEntity.status(HttpStatus.CREATED).body(contribution);
    }
}
