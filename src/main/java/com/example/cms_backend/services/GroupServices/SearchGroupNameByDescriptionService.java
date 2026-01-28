package com.example.cms_backend.services.GroupServices;

import com.example.cms_backend.abstractions.Query;
import com.example.cms_backend.model.Commands.SearchGroupNameByDescriptionCommand;
import com.example.cms_backend.model.Entities.Group;
import com.example.cms_backend.repositories.GroupRepository;
import com.example.cms_backend.repositories.UserRepository;
import com.example.cms_backend.utils.LoggedInUserUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchGroupNameByDescriptionService implements Query<SearchGroupNameByDescriptionCommand, List<Group>> {
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    public SearchGroupNameByDescriptionService(GroupRepository groupRepository, UserRepository userRepository) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<List<Group>> execute(SearchGroupNameByDescriptionCommand command) {
        List<Group> groupsByDescription = groupRepository.findByNameContainingIgnoreCaseAndDescription(
                command.getGroupName(), command.getDescription());

        HttpServletRequest request = command.getRequest();

        if (request != null) {
            String email = LoggedInUserUtil.loggedInUserEmail(request);
            if (email != null) {
                userRepository.findByEmail(email).ifPresent(user -> {
                    Long parishId = user.getParishId();
                    if (parishId != null) {
                        groupsByDescription.removeIf(group -> !parishId.equals(group.getParishId()));
                    }
                });
            }
        }

        return ResponseEntity.ok(groupsByDescription);
    }
}

