package com.example.cms_backend.Services.GroupServices;

import com.example.cms_backend.Abstractions.Query;
import com.example.cms_backend.Model.Entities.Group;
import com.example.cms_backend.Model.Entities.User;
import com.example.cms_backend.Repositories.GroupRepository;
import com.example.cms_backend.Repositories.UserRepository;
import com.example.cms_backend.Utils.LoggedInUserUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CountGroupsService implements Query<String,Long> {
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    public CountGroupsService(GroupRepository groupRepository, UserRepository userRepository) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<Long> execute(String description) {
        return execute(description, null);
    }

    public ResponseEntity<Long> execute(String description, HttpServletRequest request) {
        // If no request is provided, return total count
        if (request == null) {
            return ResponseEntity.ok(groupRepository.countByDescription(description));
        }

        // Get logged-in user's email
        String email = LoggedInUserUtil.loggedInUserEmail(request);
        if (email == null) {
            return ResponseEntity.ok(groupRepository.countByDescription(description));
        }

        // Find the logged-in user
        Optional<User> loggedInUserOptional = userRepository.findByEmail(email);
        if (loggedInUserOptional.isEmpty()) {
            return ResponseEntity.ok(groupRepository.countByDescription(description));
        }

        // Get the logged-in user's parishId
        User loggedInUser = loggedInUserOptional.get();
        Long parishId = loggedInUser.getParishId();
        if (parishId == null) {
            return ResponseEntity.ok(groupRepository.countByDescription(description));
        }

        // Count groups with the same description and parishId
        List<Group> allGroups = groupRepository.findByDescription(description);
        long count = allGroups.stream()
                .filter(group -> parishId.equals(group.getParishId()))
                .count();

        return ResponseEntity.ok(count);
    }
}
