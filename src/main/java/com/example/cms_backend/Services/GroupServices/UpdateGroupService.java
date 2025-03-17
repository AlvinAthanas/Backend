package com.example.cms_backend.Services.GroupServices;

import com.example.cms_backend.Repositories.GroupRepository;
import org.springframework.stereotype.Service;

@Service
public class UpdateGroupService {
    private final GroupRepository groupRepository;
    public UpdateGroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }
}
