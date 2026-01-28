package com.example.cms_backend.services.GroupServices;

import com.example.cms_backend.abstractions.Query;
import com.example.cms_backend.exceptions.GroupNotFoundException;
import com.example.cms_backend.model.Entities.Group;
import com.example.cms_backend.repositories.GroupRepository;
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
