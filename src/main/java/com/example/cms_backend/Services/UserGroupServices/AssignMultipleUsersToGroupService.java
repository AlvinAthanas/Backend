package com.example.cms_backend.Services.UserGroupServices;

import com.example.cms_backend.Abstractions.Command;
import com.example.cms_backend.Exceptions.GroupNotFoundException;
import com.example.cms_backend.Exceptions.UserNotFoundException;
import com.example.cms_backend.Model.Commands.AssignMultipleUsersToGroupCommand;
import com.example.cms_backend.Model.Entities.Group;
import com.example.cms_backend.Model.Entities.User;
import com.example.cms_backend.Repositories.GroupRepository;
import com.example.cms_backend.Repositories.UserRepository;
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
