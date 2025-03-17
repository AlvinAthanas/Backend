package com.example.cms_backend.Services.GroupServices;

import com.example.cms_backend.Repositories.GroupRepository;
import org.springframework.stereotype.Service;

@Service
public class GetGroupsService {
    private final GroupRepository groupRepository;

    public GetGroupsService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }
}
