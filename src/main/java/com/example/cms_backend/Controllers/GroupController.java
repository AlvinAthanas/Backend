package com.example.cms_backend.Controllers;

import com.example.cms_backend.Model.Entities.Group;
import com.example.cms_backend.Model.UpdateCommands.UpdateGroupCommand;
import com.example.cms_backend.Services.GroupServices.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GroupController {

    private final CreateGroupService createGroupService;
    private final UpdateGroupService updateGroupService;
    private final DeleteGroupService deleteGroupService;
    private final GetGroupService getGroupService;
    private final GetGroupsService getGroupsService;

    public GroupController(CreateGroupService createGroupService,
                           UpdateGroupService updateGroupService,
                           DeleteGroupService deleteGroupService,
                           GetGroupService getGroupService,
                           GetGroupsService getGroupsService) {
        this.createGroupService = createGroupService;
        this.updateGroupService = updateGroupService;
        this.deleteGroupService = deleteGroupService;
        this.getGroupService = getGroupService;
        this.getGroupsService = getGroupsService;
    }

    @PostMapping("/group")
    public ResponseEntity<Group> addGroup(@RequestBody Group group) {
        return createGroupService.execute(group);
    }

    @GetMapping("/group/{id}")
    public ResponseEntity<Group> getGroup(@PathVariable Long id) {
        return getGroupService.execute(id);
    }

    @GetMapping("/groups")
    public ResponseEntity<List<Group>> getAllGroups() {
        return getGroupsService.execute(null);
    }

    @PutMapping("/group/{id}")
    public ResponseEntity<Group> updateGroup(@PathVariable Long id, @RequestBody Group group) {
        return updateGroupService.execute(new UpdateGroupCommand(id, group));
    }

    @DeleteMapping("/group/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable Long id) {
        return deleteGroupService.execute(id);
    }
}
