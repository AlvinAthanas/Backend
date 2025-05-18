package com.example.cms_backend.Services.ContributionServices;

import com.example.cms_backend.Abstractions.Query;
import com.example.cms_backend.Model.Entities.Contribution;
import com.example.cms_backend.Model.Entities.User;
import com.example.cms_backend.Repositories.ContributionRepository;
import com.example.cms_backend.Repositories.UserRepository;
import com.example.cms_backend.Utils.LoggedInUserUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GetContributionsService implements Query<Void, List<Contribution>> {
    private final ContributionRepository contributionRepository;
    private final UserRepository userRepository;

    public GetContributionsService(ContributionRepository contributionRepository, UserRepository userRepository) {
        this.contributionRepository = contributionRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<List<Contribution>> execute(Void input) {
        List<Contribution> contributions = contributionRepository.findAll();
        return ResponseEntity.ok(contributions);
    }

    public ResponseEntity<List<Contribution>> execute(Void input, HttpServletRequest request) {
        String email = LoggedInUserUtil.loggedInUserEmail(request);
        if (email != null) {
            Optional<User> userOptional = userRepository.findByEmail(email);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                List<Contribution> contributions = contributionRepository.findContributionByParishId(user.getParishId());
                return ResponseEntity.ok(contributions);
            }
        }
        // If no user is found or the email is null, return all contributions (or you could return an empty list)
        return execute(input);
    }
}
