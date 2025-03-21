package com.example.cms_backend.Services.GroupServices;

import com.example.cms_backend.Abstractions.Query;
import com.example.cms_backend.Model.Entities.Group;
import com.example.cms_backend.Repositories.GroupRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetGroupsService implements Query<Void, List<Group>> {
    private final GroupRepository groupRepository;

    public GetGroupsService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public ResponseEntity<List<Group>> execute(Void input) {
        List<Group> groups = groupRepository.findAll();
        return ResponseEntity.ok(groups);
    }
}
