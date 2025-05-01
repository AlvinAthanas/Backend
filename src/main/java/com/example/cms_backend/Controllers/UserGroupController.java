package com.example.cms_backend.Controllers;

import com.example.cms_backend.Model.Commands.AssignGroupCommand;
import com.example.cms_backend.Model.Commands.GroupNameDescriptionCommand;
import com.example.cms_backend.Model.DTO.UserDTO;
import com.example.cms_backend.Services.UserGroupServices.AssignMemberToGroupService;
import com.example.cms_backend.Services.UserGroupServices.CountMembersOfGroupWithDescriptionService;
import com.example.cms_backend.Services.UserGroupServices.GetMembersOfGroupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserGroupController {
    private final GetMembersOfGroupService getMembersOfGroupService;
    private final CountMembersOfGroupWithDescriptionService countMembersOfGroupWithDescriptionService;
    private final AssignMemberToGroupService assignMemberToGroupService;


    public UserGroupController(GetMembersOfGroupService getMembersOfGroupService,
                               CountMembersOfGroupWithDescriptionService countMembersOfGroupWithDescriptionService,
                               AssignMemberToGroupService assignMemberToGroupService) {
        this.getMembersOfGroupService = getMembersOfGroupService;
        this.countMembersOfGroupWithDescriptionService = countMembersOfGroupWithDescriptionService;
        this.assignMemberToGroupService = assignMemberToGroupService;
    }

    @GetMapping("/group/members")
    public ResponseEntity<List<UserDTO>> getGroupMembersByDescriptionAndName(
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String name
    ) {
        return getMembersOfGroupService.execute(new GroupNameDescriptionCommand(description, name));
    }

    @GetMapping("/group/members/count")
    public ResponseEntity<Long> countGroupMembersByDescriptionAndName(
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String name
    ) {
        return countMembersOfGroupWithDescriptionService.execute(new GroupNameDescriptionCommand(description, name));
    }


    @PostMapping("/group/assignMember")
    public ResponseEntity<String> assignMember(@RequestBody AssignGroupCommand command) {
        return assignMemberToGroupService.execute(new AssignGroupCommand(command.getUserId(), command.getGroupId()));
    }

}
