package com.example.cms_backend.Services.GroupServices;

import com.example.cms_backend.Abstractions.Command;
import com.example.cms_backend.Exceptions.GroupNotFoundException;
import com.example.cms_backend.Model.Entities.Group;
import com.example.cms_backend.Model.Commands.UpdateGroupCommand;
import com.example.cms_backend.Repositories.GroupRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UpdateGroupService implements Command<UpdateGroupCommand, Group> {
    private final GroupRepository groupRepository;
    public UpdateGroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public ResponseEntity<Group> execute(UpdateGroupCommand command) {
        Optional<Group> groupOptional = groupRepository.findById(command.getId());
        if (groupOptional.isPresent()) {
            Group group = command.getGroup();
            group.setId(command.getId());
            return ResponseEntity.ok(groupRepository.save(group));
        }
        throw new GroupNotFoundException();
    }
}
