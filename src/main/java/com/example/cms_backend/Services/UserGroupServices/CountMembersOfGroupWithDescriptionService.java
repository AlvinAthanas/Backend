package com.example.cms_backend.Services.UserGroupServices;

import com.example.cms_backend.Abstractions.Query;
import com.example.cms_backend.Model.Commands.GroupNameDescriptionCommand;
import com.example.cms_backend.Repositories.UserRepository;
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

