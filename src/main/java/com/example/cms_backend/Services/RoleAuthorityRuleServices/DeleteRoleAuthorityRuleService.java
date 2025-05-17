package com.example.cms_backend.Services.RoleAuthorityRuleServices;

import com.example.cms_backend.Abstractions.Command;
import com.example.cms_backend.Model.Entities.RoleAuthorityRule;
import com.example.cms_backend.Model.Entities.User;
import com.example.cms_backend.Repositories.RoleAuthorityRuleRepository;
import com.example.cms_backend.Repositories.UserRepository;
import com.example.cms_backend.Services.UserAuthorityServices.RoleBasedAuthorityService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service to delete a role-authority rule.
 */
@Service
public class DeleteRoleAuthorityRuleService implements Command<Long, Void> {
    private final RoleAuthorityRuleRepository ruleRepository;
    private final UserRepository userRepository;
    private final RoleBasedAuthorityService roleBasedAuthorityService;

    public DeleteRoleAuthorityRuleService(RoleAuthorityRuleRepository ruleRepository,
                                         UserRepository userRepository,
                                         RoleBasedAuthorityService roleBasedAuthorityService) {
        this.ruleRepository = ruleRepository;
        this.userRepository = userRepository;
        this.roleBasedAuthorityService = roleBasedAuthorityService;
    }

    /**
     * Execute the command to delete a role-authority rule.
     * 
     * @param id The ID of the rule to delete
     * @return ResponseEntity with no content if successful, or 404 if not found
     */
    @Override
    public ResponseEntity<Void> execute(Long id) {
        Optional<RoleAuthorityRule> ruleOpt = ruleRepository.findById(id);
        if (ruleOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        String roleName = ruleOpt.get().getRoleName();
        ruleRepository.deleteById(id);
        
        // Update authorities for all users with this role
        updateAuthoritiesForUsersWithRole(roleName);
        
        return ResponseEntity.noContent().build();
    }
    
    /**
     * Update authorities for all users with a specific role.
     * @param roleName The name of the role
     */
    private void updateAuthoritiesForUsersWithRole(String roleName) {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            boolean hasRole = user.getRoles().stream()
                    .anyMatch(role -> role.getName().equals(roleName));
            
            if (hasRole) {
                roleBasedAuthorityService.updateAuthoritiesBasedOnRoles(user);
            }
        }
    }
}