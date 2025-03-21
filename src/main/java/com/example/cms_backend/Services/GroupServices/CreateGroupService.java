package com.example.cms_backend.Services.GroupServices;

import com.example.cms_backend.Abstractions.Command;
import com.example.cms_backend.Model.Entities.Group;
import com.example.cms_backend.Repositories.GroupRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CreateGroupService implements Command<Group,Group> {
    private final GroupRepository groupRepository;

    public CreateGroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }


    @Override
    public ResponseEntity<Group> execute(Group group) {
        groupRepository.save(group);
        return ResponseEntity.status(HttpStatus.CREATED).body(group);
    }
}
