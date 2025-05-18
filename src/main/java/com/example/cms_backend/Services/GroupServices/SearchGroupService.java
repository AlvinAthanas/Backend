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
import java.util.stream.Collectors;

@Service
public class SearchGroupService implements Query<String, List<Group>> {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    public SearchGroupService(GroupRepository groupRepository, UserRepository userRepository) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<List<Group>> execute(String name) {
        return execute(name, null);
    }

    public ResponseEntity<List<Group>> execute(String name, HttpServletRequest request) {
        List<Group> groups = groupRepository.findByNameContaining(name);

        // Filter groups by parishId if request is provided
        if (request != null) {
            String email = LoggedInUserUtil.loggedInUserEmail(request);
            if (email != null) {
                Optional<User> loggedInUserOptional = userRepository.findByEmail(email);
                if (loggedInUserOptional.isPresent()) {
                    User loggedInUser = loggedInUserOptional.get();
                    Long parishId = loggedInUser.getParishId();

                    if (parishId != null) {
                        // Filter groups by parishId
                        groups = groups.stream()
                                .filter(group -> parishId.equals(group.getParishId()))
                                .collect(Collectors.toList());
                    }
                }
            }
        }

        return ResponseEntity.ok(groups);
    }
}
