package com.example.cms_backend.Services.UserGroupServices;

import com.example.cms_backend.Abstractions.Command;
import com.example.cms_backend.Exceptions.GroupNotFoundException;
import com.example.cms_backend.Exceptions.UserNotFoundException;
import com.example.cms_backend.Model.Commands.AssignGroupCommand;
import com.example.cms_backend.Model.Entities.Group;
import com.example.cms_backend.Model.Entities.User;
import com.example.cms_backend.Repositories.GroupRepository;
import com.example.cms_backend.Repositories.UserRepository;
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
