package com.example.cms_backend.controllers;

import com.example.cms_backend.model.Commands.AssignGroupCommand;
import com.example.cms_backend.model.Commands.AssignMultipleUsersToGroupCommand;
import com.example.cms_backend.model.Commands.GroupNameDescriptionCommand;
import com.example.cms_backend.model.Commands.RemoveGroupCommand;
import com.example.cms_backend.model.DTO.GroupDTO;
import com.example.cms_backend.model.DTO.UserDTO;
import com.example.cms_backend.services.UserGroupServices.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost")
@PreAuthorize("hasAnyRole('PARISHIONER','COMMITTEE_CHAIRPERSON','COMMUNITY_CHAIRPERSON','COMMUNITY_SECRETARY','COMMUNITY_TREASURER') or hasAuthority('WRITE_COMMUNITIES')")
public class UserGroupController {
    private final GetMembersOfGroupService getMembersOfGroupService;
    private final CountMembersOfGroupWithDescriptionService countMembersOfGroupWithDescriptionService;
    private final AssignMemberToGroupService assignMemberToGroupService;
    private final DeleteMemberFromGroupService deleteMemberFromGroupService;
    private final GetAllUserGroupsService getAllUserGroupsService;
    private final GetUserCommunityService getUserCommunityService;
    private final AssignMultipleUsersToGroupService assignMultipleUsersToGroupService;


    public UserGroupController(GetMembersOfGroupService getMembersOfGroupService,
                               CountMembersOfGroupWithDescriptionService countMembersOfGroupWithDescriptionService,
                               AssignMemberToGroupService assignMemberToGroupService,
                               DeleteMemberFromGroupService deleteMemberFromGroupService,
                               GetAllUserGroupsService getAllUserGroupsService,
                               GetUserCommunityService getUserCommunityService,
                               AssignMultipleUsersToGroupService assignMultipleUsersToGroupService) {
        this.getMembersOfGroupService = getMembersOfGroupService;
        this.countMembersOfGroupWithDescriptionService = countMembersOfGroupWithDescriptionService;
        this.assignMemberToGroupService = assignMemberToGroupService;
        this.deleteMemberFromGroupService = deleteMemberFromGroupService;
        this.getAllUserGroupsService = getAllUserGroupsService;
        this.getUserCommunityService = getUserCommunityService;
        this.assignMultipleUsersToGroupService = assignMultipleUsersToGroupService;
    }

    @PreAuthorize("hasAuthority('READ_COMMUNITIES')")
    @GetMapping("/group/members")
    public ResponseEntity<List<UserDTO>> getGroupMembersByDescriptionAndName(
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String name
    ) {
        return getMembersOfGroupService.execute(new GroupNameDescriptionCommand(description, name));
    }

    @PreAuthorize("hasAuthority('READ_COMMUNITIES')")
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

    @PostMapping("/group/assignMultipleMembers")
    public ResponseEntity<String> assignMultipleMembers(@RequestBody AssignMultipleUsersToGroupCommand command) {
        return assignMultipleUsersToGroupService.execute(command);
    }


    @PreAuthorize("hasAuthority('READ_COMMUNITIES')")
    @GetMapping("/user/{userId}/groups")
    public ResponseEntity<List<GroupDTO>> getUserGroups(@PathVariable Long userId) {
        return getAllUserGroupsService.execute(userId);
    }

    @PreAuthorize("hasAuthority('READ_COMMUNITIES')")
    @GetMapping("/user/{userId}/community")
    public ResponseEntity<GroupDTO> getUserCommunity(@PathVariable Long userId) {
        return getUserCommunityService.execute(userId);
    }


    @DeleteMapping("/user/{userId}/group/{groupId}")
    public ResponseEntity<String> removeUserFromGroup(@PathVariable Long userId, @PathVariable Long groupId) {
        return deleteMemberFromGroupService.execute(new RemoveGroupCommand(userId, groupId));
    }


}
