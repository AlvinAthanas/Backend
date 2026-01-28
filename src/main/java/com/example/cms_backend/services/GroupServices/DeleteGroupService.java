package com.example.cms_backend.services.GroupServices;

import com.example.cms_backend.abstractions.Command;
import com.example.cms_backend.exceptions.GroupNotFoundException;
import com.example.cms_backend.model.Entities.Group;
import com.example.cms_backend.repositories.GroupRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeleteGroupService implements Command<Long,Void> {
    private final GroupRepository groupRepository;

    public DeleteGroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public ResponseEntity<Void> execute(Long id) {
        Optional<Group> groupOptional = groupRepository.findById(id);
        if (groupOptional.isPresent()) {
            groupRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        throw new GroupNotFoundException();
    }
}
