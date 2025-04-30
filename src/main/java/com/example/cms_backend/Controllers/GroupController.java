package com.example.cms_backend.Controllers;

import com.example.cms_backend.Model.Commands.AssignGroupCommand;
import com.example.cms_backend.Model.Commands.AssignRoleCommand;
import com.example.cms_backend.Model.Commands.SearchGroupNameByDescriptionCommand;
import com.example.cms_backend.Model.Entities.Group;
import com.example.cms_backend.Model.Commands.UpdateGroupCommand;
import com.example.cms_backend.Services.GroupServices.*;
import com.example.cms_backend.Services.UserGroupServices.AssignMemberToGroupService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GroupController {

    private final CreateGroupService createGroupService;
    private final UpdateGroupService updateGroupService;
    private final DeleteGroupService deleteGroupService;
    private final GetGroupService getGroupService;
    private final GetGroupsService getGroupsService;
    private final SearchGroupService searchGroupService;
    private final CreateGroupsService createGroupsService;
    private final CountGroupsService countGroupsService;
    private final GetGroupsByDescriptionService getGroupsByDescriptionService;
    private final SearchGroupNameByDescriptionService searchGroupNameByDescriptionService;
    private final AssignMemberToGroupService assignMemberToGroupService;

    public GroupController(CreateGroupService createGroupService,
                           UpdateGroupService updateGroupService,
                           DeleteGroupService deleteGroupService,
                           GetGroupService getGroupService,
                           GetGroupsService getGroupsService,
                           SearchGroupService searchGroupService,
                           CreateGroupsService createGroupsService,
                           CountGroupsService countGroupsService,
                           GetGroupsByDescriptionService getGroupsByDescriptionService,
                           SearchGroupNameByDescriptionService searchGroupNameByDescriptionService,
                           AssignMemberToGroupService assignMemberToGroupService) {
        this.createGroupService = createGroupService;
        this.updateGroupService = updateGroupService;
        this.deleteGroupService = deleteGroupService;
        this.getGroupService = getGroupService;
        this.getGroupsService = getGroupsService;
        this.searchGroupService = searchGroupService;
        this.createGroupsService = createGroupsService;
        this.countGroupsService = countGroupsService;
        this.getGroupsByDescriptionService = getGroupsByDescriptionService;
        this.searchGroupNameByDescriptionService = searchGroupNameByDescriptionService;
        this.assignMemberToGroupService = assignMemberToGroupService;
    }

    @PostMapping("/group")
    public ResponseEntity<Group> addGroup(@RequestBody Group group) {
        return createGroupService.execute(group);
    }

    @PostMapping("/groups")
    public ResponseEntity<List<Group>> addGroups(@RequestBody List<Group> groups) {
        return createGroupsService.execute(groups);
    }

    @GetMapping("/group/{id}")
    public ResponseEntity<Group> getGroup(@PathVariable Long id) {
        return getGroupService.execute(id);
    }

    @GetMapping("/groups")
    public ResponseEntity<List<Group>> getAllGroups() {
        return getGroupsService.execute(null);
    }

    @GetMapping("/group/search")
    public ResponseEntity<List<Group>> searchGroupByName(@RequestParam String name) {
        return searchGroupService.execute(name);
    }

    @PutMapping("/group/{id}")
    public ResponseEntity<Group> updateGroup(@PathVariable Long id, @RequestBody Group group) {
        return updateGroupService.execute(new UpdateGroupCommand(id, group));
    }

    @DeleteMapping("/group/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable Long id) {
        return deleteGroupService.execute(id);
    }

    @PreAuthorize("hasRole('ROLE_PARISH_MEMBER')")
    @GetMapping("/group/count")
    public ResponseEntity<Long> countGroup(@RequestParam String description) {
        return countGroupsService.execute(description);
    }

    @PostMapping("/group/community")
    public ResponseEntity<Group> addCommunity(@RequestBody Group group) {
        group.setDescription("community");
        return createGroupService.execute(group);
    }

    @GetMapping("/groups/communities")
    public ResponseEntity<List<Group>> getAllCommunities() {
        return getGroupsByDescriptionService.execute(null);
    }

    @GetMapping("/groupsNameByDescription/search")
    public ResponseEntity<List<Group>> searchGroupNameByDescription(
            @RequestParam String name,
            @RequestParam String description
    ) {
        return searchGroupNameByDescriptionService.execute(new SearchGroupNameByDescriptionCommand(name, description));
    }

    @PostMapping("/group/assignMember")
    public ResponseEntity<String> assignMember(@RequestBody AssignGroupCommand command) {
        return assignMemberToGroupService.execute(new AssignGroupCommand(command.getUserId(), command.getGroupId()));
    }

}
