package com.example.cms_backend.Services.ContributionServices;

import com.example.cms_backend.Abstractions.Query;
import com.example.cms_backend.Model.Entities.User;
import com.example.cms_backend.Repositories.ContributionRepository;
import com.example.cms_backend.Repositories.UserRepository;
import com.example.cms_backend.Utils.LoggedInUserUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GetTotalAmountService implements Query<Void,Long> {
    private final ContributionRepository contributionRepository;
    private final UserRepository userRepository;

    public GetTotalAmountService(ContributionRepository contributionRepository, UserRepository userRepository) {
        this.contributionRepository = contributionRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<Long> execute(Void input) {
        return ResponseEntity.ok(contributionRepository.getTotalAmount());
    }

    public ResponseEntity<Long> execute(Void input, HttpServletRequest request) {
        String email = LoggedInUserUtil.loggedInUserEmail(request);
        if (email != null) {
            Optional<User> userOptional = userRepository.findByEmail(email);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                Long totalAmount = contributionRepository.getTotalAmountByParishId(user.getParishId());
                return ResponseEntity.ok(totalAmount != null ? totalAmount : 0L);
            }
        }
        // If no user is found or email is null, return the total number of all contributions
        return execute(input);
    }
}
