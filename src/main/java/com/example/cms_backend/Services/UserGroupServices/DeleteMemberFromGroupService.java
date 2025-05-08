package com.example.cms_backend.Services.UserGroupServices;

import com.example.cms_backend.Abstractions.Command;
import com.example.cms_backend.Exceptions.GroupNotFoundException;
import com.example.cms_backend.Exceptions.UserNotFoundException;
import com.example.cms_backend.Model.Commands.RemoveGroupCommand;
import com.example.cms_backend.Model.Entities.Group;
import com.example.cms_backend.Model.Entities.User;
import com.example.cms_backend.Repositories.GroupRepository;
import com.example.cms_backend.Repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeleteMemberFromGroupService implements Command<RemoveGroupCommand, String> {

    private final UserRepository userRepository;
    private final GroupRepository groupRepository;

    public DeleteMemberFromGroupService(UserRepository userRepository, GroupRepository groupRepository) {
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
    }

    @Override
    public ResponseEntity<String> execute(RemoveGroupCommand command) {
        Optional<User> user = userRepository.findById(command.getUserId());
        if (user.isEmpty()) throw new UserNotFoundException();

        Optional<Group> group = groupRepository.findById(command.getGroupId());
        if (group.isEmpty()) throw new GroupNotFoundException();

        boolean removed = user.get().getGroups().remove(group.get());
        userRepository.save(user.get());

        if (removed) {
            return ResponseEntity.ok("Successfully removed user from group: " + group.get().getName());
        } else {
            return ResponseEntity.badRequest().body("User was not a member of the specified group.");
        }
    }
}
