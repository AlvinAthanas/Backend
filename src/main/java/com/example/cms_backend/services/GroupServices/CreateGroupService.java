package com.example.cms_backend.services.GroupServices;

import com.example.cms_backend.abstractions.Command;
import com.example.cms_backend.model.Entities.Group;
import com.example.cms_backend.model.Entities.User;
import com.example.cms_backend.repositories.GroupRepository;
import com.example.cms_backend.repositories.UserRepository;
import com.example.cms_backend.utils.LoggedInUserUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CreateGroupService implements Command<Group,Group> {
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    public CreateGroupService(GroupRepository groupRepository, UserRepository userRepository) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<Group> execute(Group group) {
        return execute(group, null);
    }

    public ResponseEntity<Group> execute(Group group, HttpServletRequest request) {
        // Set parishId from logged-in user if it's null and request is provided
        if (group.getParishId() == null && request != null) {
            String email = LoggedInUserUtil.loggedInUserEmail(request);
            if (email != null) {
                Optional<User> loggedInUserOptional = userRepository.findByEmail(email);
                if (loggedInUserOptional.isPresent()) {
                    User loggedInUser = loggedInUserOptional.get();
                    group.setParishId(loggedInUser.getParishId());
                }
            }
        }

        groupRepository.save(group);
        return ResponseEntity.status(HttpStatus.CREATED).body(group);
    }
}
