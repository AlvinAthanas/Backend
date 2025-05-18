package com.example.cms_backend.Services.UserServices;

import com.example.cms_backend.Abstractions.Query;
import com.example.cms_backend.Model.Entities.User;
import com.example.cms_backend.Repositories.UserRepository;
import com.example.cms_backend.Utils.LoggedInUserUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CountUsersService implements Query<Void,Long> {
    private final UserRepository userRepository;

    public CountUsersService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<Long> execute(Void input) {
        return execute(input, null);
    }

    public ResponseEntity<Long> execute(Void input, HttpServletRequest request) {
        // If no request is provided, return total count
        if (request == null) {
            return ResponseEntity.ok(userRepository.count());
        }

        // Get logged-in user's email
        String email = LoggedInUserUtil.loggedInUserEmail(request);
        if (email == null) {
            return ResponseEntity.ok(userRepository.count());
        }

        // Find the logged-in user
        Optional<User> loggedInUserOptional = userRepository.findByEmail(email);
        if (loggedInUserOptional.isEmpty()) {
            return ResponseEntity.ok(userRepository.count());
        }

        // Get the logged-in user's parishId
        User loggedInUser = loggedInUserOptional.get();
        Long parishId = loggedInUser.getParishId();
        if (parishId == null) {
            return ResponseEntity.ok(userRepository.count());
        }

        // Count users with the same parishId
        List<User> allUsers = userRepository.findAll();
        long count = allUsers.stream()
                .filter(user -> parishId.equals(user.getParishId()))
                .count();

        return ResponseEntity.ok(count);
    }
}
