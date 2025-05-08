package com.example.cms_backend.Controllers;

import com.example.cms_backend.Model.Commands.AssignGroupCommand;
import com.example.cms_backend.Model.Commands.GroupNameDescriptionCommand;
import com.example.cms_backend.Model.Commands.RemoveGroupCommand;
import com.example.cms_backend.Model.DTO.GroupDTO;
import com.example.cms_backend.Model.DTO.UserDTO;
import com.example.cms_backend.Services.UserGroupServices.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserGroupController {
    private final GetMembersOfGroupService getMembersOfGroupService;
    private final CountMembersOfGroupWithDescriptionService countMembersOfGroupWithDescriptionService;
    private final AssignMemberToGroupService assignMemberToGroupService;
    private final DeleteMemberFromGroupService deleteMemberFromGroupService;
    private final GetAllUserGroupsService getAllUserGroupsService;
    private final GetUserCommunityService getUserCommunityService;


    public UserGroupController(GetMembersOfGroupService getMembersOfGroupService,
                               CountMembersOfGroupWithDescriptionService countMembersOfGroupWithDescriptionService,
                               AssignMemberToGroupService assignMemberToGroupService,
                               DeleteMemberFromGroupService deleteMemberFromGroupService,
                               GetAllUserGroupsService getAllUserGroupsService,
                               GetUserCommunityService getUserCommunityService) {
        this.getMembersOfGroupService = getMembersOfGroupService;
        this.countMembersOfGroupWithDescriptionService = countMembersOfGroupWithDescriptionService;
        this.assignMemberToGroupService = assignMemberToGroupService;
        this.deleteMemberFromGroupService = deleteMemberFromGroupService;
        this.getAllUserGroupsService = getAllUserGroupsService;
        this.getUserCommunityService = getUserCommunityService;
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

    @GetMapping("/user/{userId}/groups")
    public ResponseEntity<List<GroupDTO>> getUserGroups(@PathVariable Long userId) {
        return getAllUserGroupsService.execute(userId);
    }

    @GetMapping("/user/{userId}/community")
    public ResponseEntity<GroupDTO> getUserCommunity(@PathVariable Long userId) {
        return getUserCommunityService.execute(userId);
    }


    @DeleteMapping("/user/{userId}/group/{groupId}")
    public ResponseEntity<String> removeUserFromGroup(@PathVariable Long userId, @PathVariable Long groupId) {
        return deleteMemberFromGroupService.execute(new RemoveGroupCommand(userId, groupId));
    }


}
