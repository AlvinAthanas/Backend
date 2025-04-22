package com.example.cms_backend.Controllers;

import com.example.cms_backend.Model.DTO.UserDTO;
import com.example.cms_backend.Model.UpdateCommands.AssignRoleCommand;
import com.example.cms_backend.Roles_Authorities.UserRoles;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserRoleController {
    private final UserRoles userRoles;

    public UserRoleController(UserRoles userRoles) {
        this.userRoles = userRoles;
    }

    @PostMapping("/user/role/{id}")
    public ResponseEntity<String> assignRole(@PathVariable Long id, @RequestParam String roleName){
        return userRoles.execute(new AssignRoleCommand(id, roleName));
    }
}
