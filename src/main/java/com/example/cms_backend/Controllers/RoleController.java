package com.example.cms_backend.Controllers;

import com.example.cms_backend.Model.Entities.Role;
import com.example.cms_backend.Model.Commands.UpdateRoleCommand;
import com.example.cms_backend.Services.RoleServices.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RoleController {

    private final CreateRoleService createRoleService;
    private final DeleteRoleService deleteRoleService;
    private final UpdateRoleService updateRoleService;
    private final GetRoleService getRoleService;
    private final GetRolesService getRolesService;
    private final SearchRoleService searchRoleService;

    public RoleController(CreateRoleService createRoleService,
                          DeleteRoleService deleteRoleService,
                          UpdateRoleService updateRoleService,
                          GetRoleService getRoleService,
                          GetRolesService getRolesService,
                          SearchRoleService searchRoleService) {
        this.createRoleService = createRoleService;
        this.deleteRoleService = deleteRoleService;
        this.updateRoleService = updateRoleService;
        this.getRoleService = getRoleService;
        this.getRolesService = getRolesService;
        this.searchRoleService = searchRoleService;
    }

    @PostMapping("/role")
    public ResponseEntity<Role> createRole(@RequestBody Role role){
        return createRoleService.execute(role);
    }

    @GetMapping("/role/{id}")
    public ResponseEntity<Role> getRole(@PathVariable Long id){
        return getRoleService.execute(id);
    }

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getAllRoles(){
        return getRolesService.execute(null);
    }

    @GetMapping("/role/search")
    public ResponseEntity<List<Role>> searchRoleByName(@RequestParam String name){
        return searchRoleService.execute(name);
    }

    @PutMapping("/role/{id}")
    public ResponseEntity<Role> updateRole(@PathVariable Long id, @RequestBody Role role){
        return updateRoleService.execute(new UpdateRoleCommand(id, role));
    }

    @DeleteMapping("/role/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id){
        return deleteRoleService.execute(id);
    }


}
