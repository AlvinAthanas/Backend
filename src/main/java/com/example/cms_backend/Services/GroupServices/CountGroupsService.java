package com.example.cms_backend.Services.GroupServices;

import com.example.cms_backend.Abstractions.Query;
import com.example.cms_backend.Repositories.GroupRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CountGroupsService implements Query<String,Long> {
    private final GroupRepository groupRepository;

    public CountGroupsService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public ResponseEntity<Long> execute(String description) {
        return ResponseEntity.ok(groupRepository.countByDescription(description));
    }
}
