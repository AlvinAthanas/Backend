package com.example.cms_backend.Services.GroupServices;

import com.example.cms_backend.Abstractions.Command;
import com.example.cms_backend.Model.Entities.Group;
import com.example.cms_backend.Repositories.GroupRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreateGroupsService implements Command<List<Group>,List<Group>> {

    private final GroupRepository groupRepository;

    public CreateGroupsService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public ResponseEntity<List<Group>> execute(List<Group> groups) {
        groupRepository.saveAll(groups);
        return ResponseEntity.status(HttpStatus.CREATED).body(groups);
    }
}
