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

import java.util.List;
import java.util.Optional;

@Service
public class CreateGroupsService implements Command<List<Group>,List<Group>> {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    public CreateGroupsService(GroupRepository groupRepository, UserRepository userRepository) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<List<Group>> execute(List<Group> groups) {
        return execute(groups, null);
    }

    public ResponseEntity<List<Group>> execute(List<Group> groups, HttpServletRequest request) {
        // Get logged-in user's parishId if available
        Long loggedInUserParishId = null;
        if (request != null) {
            String email = LoggedInUserUtil.loggedInUserEmail(request);
            if (email != null) {
                Optional<User> loggedInUserOptional = userRepository.findByEmail(email);
                if (loggedInUserOptional.isPresent()) {
                    User loggedInUser = loggedInUserOptional.get();
                    loggedInUserParishId = loggedInUser.getParishId();
                }
            }
        }

        // Set parishId for each group if it's null and we have a logged-in user's parishId
        if (loggedInUserParishId != null) {
            for (Group group : groups) {
                if (group.getParishId() == null) {
                    group.setParishId(loggedInUserParishId);
                }
            }
        }

        groupRepository.saveAll(groups);
        return ResponseEntity.status(HttpStatus.CREATED).body(groups);
    }
}
