package com.example.cms_backend.Controllers;

import com.example.cms_backend.Model.Commands.AssignRoleCommand;
import com.example.cms_backend.Model.Commands.UpdateUserRolesCommand;
import com.example.cms_backend.Services.UserRoleServices.AssignRolesService;
import com.example.cms_backend.Services.UserRoleServices.GetUserRolesService;
import com.example.cms_backend.Services.UserRoleServices.UpdateUserRolesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
public class UserRoleController {
    private final AssignRolesService userRoles;
    private final GetUserRolesService getUserRoles;
    private final UpdateUserRolesService updateUserRoles;

    public UserRoleController(AssignRolesService assignRolesService,
                              GetUserRolesService getUserRolesService,
                              UpdateUserRolesService updateUserRolesService) {
        this.userRoles = assignRolesService;
        this.getUserRoles = getUserRolesService;
        this.updateUserRoles = updateUserRolesService;
    }

    @PostMapping("/user/role/{id}")
    public ResponseEntity<String> assignRole(@PathVariable Long id, @RequestParam String roleName){
        return userRoles.execute(new AssignRoleCommand(id, roleName));
    }

    @GetMapping("/user/{id}/roles")
    public ResponseEntity<Set<String>> getUserRoles(@PathVariable Long id) {
        return getUserRoles.execute(id);
    }

    @PutMapping("/user/{id}/roles")
    public ResponseEntity<String> updateUserRoles(@PathVariable Long id, @RequestBody Set<String> roleNames) {
        return updateUserRoles.execute(new UpdateUserRolesCommand(id, roleNames));
    }


}
