package com.example.cms_backend.services.UserGroupServices;

import com.example.cms_backend.abstractions.Query;
import com.example.cms_backend.model.Commands.GroupNameDescriptionCommand;
import com.example.cms_backend.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CountMembersOfGroupWithDescriptionService implements Query<GroupNameDescriptionCommand, Long> {
    private final UserRepository userRepository;

    public CountMembersOfGroupWithDescriptionService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<Long> execute(GroupNameDescriptionCommand command) {
        Long count = userRepository.countUsersByGroupDescriptionAndName(command.getDescription(), command.getGroupName());
        return ResponseEntity.ok(count);
    }
}

