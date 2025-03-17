package com.example.cms_backend.Services.GroupServices;

import com.example.cms_backend.Repositories.GroupRepository;
import org.springframework.stereotype.Service;

@Service
public class GetGroupService {
    private final GroupRepository groupRepository;

    public GetGroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }
}
