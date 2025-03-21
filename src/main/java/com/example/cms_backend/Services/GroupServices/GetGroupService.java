package com.example.cms_backend.Services.GroupServices;

import com.example.cms_backend.Abstractions.Query;
import com.example.cms_backend.Exceptions.GroupNotFoundException;
import com.example.cms_backend.Model.Entities.Group;
import com.example.cms_backend.Repositories.GroupRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GetGroupService implements Query<Long, Group> {
    private final GroupRepository groupRepository;

    public GetGroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public ResponseEntity<Group> execute(Long id) {
        Optional<Group> groupOptional = groupRepository.findById(id);
        if (groupOptional.isPresent()) {
            return ResponseEntity.ok(groupOptional.get());
        }
        throw new GroupNotFoundException();
    }
}
