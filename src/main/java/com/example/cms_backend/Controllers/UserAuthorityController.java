package com.example.cms_backend.Controllers;

import com.example.cms_backend.Model.Commands.AssignAuthorityCommand;
import com.example.cms_backend.Model.Commands.UpdateUserAuthoritiesCommand;
import com.example.cms_backend.Services.UserAuthorityServices.AssignAuthoritiesService;
import com.example.cms_backend.Services.UserAuthorityServices.GetUserAuthoritiesService;
import com.example.cms_backend.Services.UserAuthorityServices.UpdateUserAuthoritiesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
public class UserAuthorityController {
    private final AssignAuthoritiesService userAuthorities;
    private final GetUserAuthoritiesService getUserAuthorities;
    private final UpdateUserAuthoritiesService updateUserAuthorities;

    public UserAuthorityController(AssignAuthoritiesService assignAuthoritiesService,
                                  GetUserAuthoritiesService getUserAuthoritiesService,
                                  UpdateUserAuthoritiesService updateUserAuthoritiesService) {
        this.userAuthorities = assignAuthoritiesService;
        this.getUserAuthorities = getUserAuthoritiesService;
        this.updateUserAuthorities = updateUserAuthoritiesService;
    }

    @PostMapping("/user/authority/{id}")
    public ResponseEntity<String> assignAuthority(@PathVariable Long id, @RequestParam String authorityName){
        return userAuthorities.execute(new AssignAuthorityCommand(id, authorityName));
    }

    @GetMapping("/user/{id}/authorities")
    public ResponseEntity<Set<String>> getUserAuthorities(@PathVariable Long id) {
        return getUserAuthorities.execute(id);
    }

    @PutMapping("/user/{id}/authorities")
    public ResponseEntity<String> updateUserAuthorities(@PathVariable Long id, @RequestBody Set<String> authorityNames) {
        return updateUserAuthorities.execute(new UpdateUserAuthoritiesCommand(id, authorityNames));
    }
}