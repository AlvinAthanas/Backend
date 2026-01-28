package com.example.cms_backend.services.UserGroupServices;

import com.example.cms_backend.abstractions.Command;
import com.example.cms_backend.exceptions.GroupNotFoundException;
import com.example.cms_backend.exceptions.UserNotFoundException;
import com.example.cms_backend.model.Commands.AssignMultipleUsersToGroupCommand;
import com.example.cms_backend.model.Entities.Group;
import com.example.cms_backend.model.Entities.User;
import com.example.cms_backend.repositories.GroupRepository;
import com.example.cms_backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AssignMultipleUsersToGroupService implements Command<AssignMultipleUsersToGroupCommand, String> {

    private final UserRepository userRepository;
    private final GroupRepository groupRepository;

    @Override
    public ResponseEntity<String> execute(AssignMultipleUsersToGroupCommand command) {
        Group group = groupRepository.findById(command.getGroupId())
                .orElseThrow(GroupNotFoundException::new);

        List<User> users = userRepository.findAllById(command.getUserIds());

        if (users.isEmpty()) {
            throw new UserNotFoundException();
        }

        for (User user : users) {
            if (user.getGroups() == null) {
                user.setGroups(new HashSet<>());
            }
            user.getGroups().add(group);
        }

        userRepository.saveAll(users);

        return ResponseEntity.ok("Successfully assigned " + users.size() + " user(s) to group: " + group.getName());
    }
}
