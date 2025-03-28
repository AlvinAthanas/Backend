package com.example.cms_backend.Services.GroupServices;

import com.example.cms_backend.Abstractions.Query;
import com.example.cms_backend.Model.Entities.Group;
import com.example.cms_backend.Repositories.GroupRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SearchGroupService implements Query<String, List<Group>> {

    private final GroupRepository groupRepository;

    public SearchGroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public ResponseEntity<List<Group>> execute(String name) {
        List<Group> groups = groupRepository.findByNameContaining(name);
        return ResponseEntity.ok(groups);
    }
}
