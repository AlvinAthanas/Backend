package com.example.cms_backend.Services.GroupServices;

import com.example.cms_backend.Abstractions.Query;
import com.example.cms_backend.Model.Commands.SearchGroupNameByDescriptionCommand;
import com.example.cms_backend.Model.Entities.Group;
import com.example.cms_backend.Repositories.GroupRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SearchGroupNameByDescriptionService implements Query<SearchGroupNameByDescriptionCommand, List<Group>> {
    private final GroupRepository groupRepository;

    public SearchGroupNameByDescriptionService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public ResponseEntity<List<Group>> execute(SearchGroupNameByDescriptionCommand command) {
        List<Group> groupsByDescription = groupRepository.findByNameContainingIgnoreCaseAndDescription(command.getGroupName(), command.getDescription());
        return ResponseEntity.ok(groupsByDescription);
    }
}
