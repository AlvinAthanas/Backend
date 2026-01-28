package com.example.cms_backend.services.UserGroupServices;

import com.example.cms_backend.abstractions.Command;
import com.example.cms_backend.exceptions.GroupNotFoundException;
import com.example.cms_backend.exceptions.UserNotFoundException;
import com.example.cms_backend.model.Commands.AssignGroupCommand;
import com.example.cms_backend.model.Entities.Group;
import com.example.cms_backend.model.Entities.User;
import com.example.cms_backend.repositories.GroupRepository;
import com.example.cms_backend.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;

@Service
public class AssignMemberToGroupService implements Command<AssignGroupCommand, String> {
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;

    public AssignMemberToGroupService(UserRepository userRepository, GroupRepository groupRepository) {
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
    }

    @Override
    public ResponseEntity<String> execute(AssignGroupCommand command) {
        Optional<User> user = userRepository.findById(command.getUserId());
        if (user.isPresent()) {
            Optional<Group> group = groupRepository.findById(command.getGroupId());
            if (group.isPresent()) {
                if (user.get().getGroups() == null){
                    user.get().setGroups(new HashSet<>());
                }
                user.get().getGroups().add(group.get());
                userRepository.save(user.get());
                return ResponseEntity.ok().body("Successfully assigned group: " + group.get().getName());
            }
            else {
                throw new GroupNotFoundException();
            }
        }
        else {
            throw new UserNotFoundException();
        }
    }
}
