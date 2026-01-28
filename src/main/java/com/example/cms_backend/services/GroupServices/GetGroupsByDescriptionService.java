package com.example.cms_backend.services.GroupServices;

import com.example.cms_backend.abstractions.Query;
import com.example.cms_backend.model.Entities.Group;
import com.example.cms_backend.model.Entities.User;
import com.example.cms_backend.repositories.GroupRepository;
import com.example.cms_backend.repositories.UserRepository;
import com.example.cms_backend.utils.LoggedInUserUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GetGroupsByDescriptionService implements Query<Void, List<Group>> {
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    public GetGroupsByDescriptionService(GroupRepository groupRepository, UserRepository userRepository) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<List<Group>> execute(Void input) {
        return execute(input, null);
    }

    public ResponseEntity<List<Group>> execute(Void input, HttpServletRequest request) {
        List<Group> communities = groupRepository.findByDescription("community");

        // Filter communities by parishId if request is provided
        if (request != null) {
            String email = LoggedInUserUtil.loggedInUserEmail(request);
            if (email != null) {
                Optional<User> loggedInUserOptional = userRepository.findByEmail(email);
                if (loggedInUserOptional.isPresent()) {
                    User loggedInUser = loggedInUserOptional.get();
                    Long parishId = loggedInUser.getParishId();

                    if (parishId != null) {
                        // Filter communities by parishId
                        communities = communities.stream()
                                .filter(group -> parishId.equals(group.getParishId()))
                                .collect(Collectors.toList());
                    }
                }
            }
        }

        return ResponseEntity.ok(communities);
    }
}
