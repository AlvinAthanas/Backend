package com.example.cms_backend.Services.RoleAuthorityRuleServices;

import com.example.cms_backend.Abstractions.Query;
import com.example.cms_backend.Model.Entities.RoleAuthorityRule;
import com.example.cms_backend.Repositories.RoleAuthorityRuleRepository;
import com.example.cms_backend.Services.UserAuthorityServices.RoleBasedAuthorityService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service to retrieve a role-authority rule by role name.
 */
@Service
public class GetRoleAuthorityRuleByRoleNameService implements Query<String, RoleAuthorityRule> {
    private final RoleAuthorityRuleRepository ruleRepository;
    private final RoleBasedAuthorityService roleBasedAuthorityService;

    public GetRoleAuthorityRuleByRoleNameService(
            RoleAuthorityRuleRepository ruleRepository,
            RoleBasedAuthorityService roleBasedAuthorityService) {
        this.ruleRepository = ruleRepository;
        this.roleBasedAuthorityService = roleBasedAuthorityService;
    }

    /**
     * Execute the query to retrieve a role-authority rule by role name.
     * If there is no rule for the particular role name in the database,
     * it returns the default rules for that role.
     * 
     * @param roleName The name of the role
     * @return ResponseEntity containing the rule (custom or default)
     */
    @Override
    public ResponseEntity<RoleAuthorityRule> execute(String roleName) {
        Optional<RoleAuthorityRule> ruleOpt = ruleRepository.findByRoleName(roleName);

        if (ruleOpt.isPresent()) {
            // Return the custom rule if found
            return ResponseEntity.ok(ruleOpt.get());
        } else {
            // Create and return a default rule if not found
            RoleAuthorityRule defaultRule = roleBasedAuthorityService.createDefaultRuleForRole(roleName);
            return ResponseEntity.ok(defaultRule);
        }
    }
}
