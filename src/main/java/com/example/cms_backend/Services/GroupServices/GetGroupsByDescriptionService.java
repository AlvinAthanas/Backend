package com.example.cms_backend.Services.GroupServices;

import com.example.cms_backend.Abstractions.Query;
import com.example.cms_backend.Model.Entities.Group;
import com.example.cms_backend.Repositories.GroupRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class GetGroupsByDescriptionService implements Query<Void, List<Group>> {
    private final GroupRepository groupRepository;

    public GetGroupsByDescriptionService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public ResponseEntity<List<Group>> execute(Void input) {
        List<Group> communities = groupRepository.findByDescription("community");
        return ResponseEntity.ok(communities);
    }
}
