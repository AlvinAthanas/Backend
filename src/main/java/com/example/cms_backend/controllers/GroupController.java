package com.example.cms_backend.controllers;

import com.example.cms_backend.model.Commands.SearchGroupNameByDescriptionCommand;
import com.example.cms_backend.model.Entities.Group;
import com.example.cms_backend.model.Commands.UpdateGroupCommand;
import com.example.cms_backend.services.GroupServices.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize("hasRole('PARISHIONER') or hasRole('COMMITTEE_CHAIRPERSON') or hasRole('COMMITTEE_SECRETARY')")
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

    public GroupController(CreateGroupService createGroupService,
                           UpdateGroupService updateGroupService,
                           DeleteGroupService deleteGroupService,
                           GetGroupService getGroupService,
                           GetGroupsService getGroupsService,
                           SearchGroupService searchGroupService,
                           CreateGroupsService createGroupsService,
                           CountGroupsService countGroupsService,
                           GetGroupsByDescriptionService getGroupsByDescriptionService,
                           SearchGroupNameByDescriptionService searchGroupNameByDescriptionService) {
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
    }

    @PreAuthorize("hasAuthority('WRITE_COMMUNITIES')")
    @PostMapping("/group")
    public ResponseEntity<Group> addGroup(@RequestBody Group group, HttpServletRequest request) {
        return createGroupService.execute(group, request);
    }

    @PreAuthorize("hasAuthority('WRITE_COMMUNITIES')")
    @PostMapping("/groups")
    public ResponseEntity<List<Group>> addGroups(@RequestBody List<Group> groups, HttpServletRequest request) {
        return createGroupsService.execute(groups, request);
    }


    @GetMapping("/group/{id}")
    public ResponseEntity<Group> getGroup(@PathVariable Long id) {
        return getGroupService.execute(id);
    }

    @PreAuthorize("hasAuthority('READ_COMMUNITIES')")
    @GetMapping("/groups")
    public ResponseEntity<List<Group>> getAllGroups(HttpServletRequest request) {
        return getGroupsService.execute(null, request);
    }

    @GetMapping("/group/search")
    public ResponseEntity<List<Group>> searchGroupByName(@RequestParam String name, HttpServletRequest request) {
        return searchGroupService.execute(name, request);
    }

    @PutMapping("/group/{id}")
    public ResponseEntity<Group> updateGroup(@PathVariable Long id, @RequestBody Group group, HttpServletRequest request) {
        return updateGroupService.execute(new UpdateGroupCommand(id, group), request);
    }

    @DeleteMapping("/group/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable Long id) {
        return deleteGroupService.execute(id);
    }

    @PreAuthorize("hasAuthority('READ_COMMUNITIES')")
    @GetMapping("/group/count")
    public ResponseEntity<Long> countGroup(@RequestParam String description, HttpServletRequest request) {
        return countGroupsService.execute(description, request);
    }

    @PostMapping("/group/community")
    public ResponseEntity<Group> addCommunity(@RequestBody Group group, HttpServletRequest request) {
        group.setDescription("community");
        return createGroupService.execute(group, request);
    }

    @GetMapping("/groups/communities")
    public ResponseEntity<List<Group>> getAllCommunities(HttpServletRequest request) {
        return getGroupsByDescriptionService.execute(null, request);
    }

    @GetMapping("/groupsNameByDescription/search")
    public ResponseEntity<List<Group>> searchGroupNameByDescription(
            @RequestParam String name,
            @RequestParam String description,
            HttpServletRequest request
    ) {
        SearchGroupNameByDescriptionCommand command =
                new SearchGroupNameByDescriptionCommand(name, description, request);
        return searchGroupNameByDescriptionService.execute(command);
    }



}
