package com.example.cms_backend.services.ContributionServices;

import com.example.cms_backend.abstractions.Query;
import com.example.cms_backend.model.DTO.ContributionFilterDTO;
import com.example.cms_backend.model.Entities.Contribution;
import com.example.cms_backend.model.Entities.User;
import com.example.cms_backend.repositories.ContributionRepository;
import com.example.cms_backend.repositories.UserRepository;
import com.example.cms_backend.utils.LoggedInUserUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GetFilteredContributionsService implements Query<ContributionFilterDTO, List<Contribution>> {

    private final ContributionRepository contributionRepository;
    private final UserRepository userRepository;

    public GetFilteredContributionsService(ContributionRepository contributionRepository, UserRepository userRepository) {
        this.contributionRepository = contributionRepository;
        this.userRepository = userRepository;
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

    public ResponseEntity<List<Contribution>> execute(ContributionFilterDTO input, HttpServletRequest request) {
        String email = LoggedInUserUtil.loggedInUserEmail(request);
        if (email != null) {
            Optional<User> userOptional = userRepository.findByEmail(email);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                List<Contribution> results = contributionRepository.filterByTypeMonthYear(
                        input.getType() != null && !input.getType().isEmpty() ? input.getType() : null,
                        input.getMonth(),
                        input.getYear()
                );
                // Filter results by parishId
                results = results.stream()
                        .filter(contribution -> contribution.getParishId() != null && contribution.getParishId().equals(user.getParishId()))
                        .toList();
                return ResponseEntity.ok(results);
            }
        }
        // If no user is found or email is null, return all filtered contributions
        return execute(input);
    }
}
