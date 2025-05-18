package com.example.cms_backend.Services.GroupServices;

import com.example.cms_backend.Abstractions.Command;
import com.example.cms_backend.Model.Entities.Group;
import com.example.cms_backend.Model.Entities.User;
import com.example.cms_backend.Repositories.GroupRepository;
import com.example.cms_backend.Repositories.UserRepository;
import com.example.cms_backend.Utils.LoggedInUserUtil;
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
