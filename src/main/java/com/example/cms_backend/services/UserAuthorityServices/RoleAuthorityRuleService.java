package com.example.cms_backend.services.UserAuthorityServices;

import com.example.cms_backend.model.Entities.RoleAuthorityRule;
import com.example.cms_backend.model.Entities.User;
import com.example.cms_backend.repositories.RoleAuthorityRuleRepository;
import com.example.cms_backend.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service to manage role-authority rule configurations.
 */
@Service
public class RoleAuthorityRuleService {
    private final RoleAuthorityRuleRepository ruleRepository;
    private final UserRepository userRepository;
    private final RoleBasedAuthorityService roleBasedAuthorityService;

    public RoleAuthorityRuleService(RoleAuthorityRuleRepository ruleRepository,
                                   UserRepository userRepository,
                                   RoleBasedAuthorityService roleBasedAuthorityService) {
        this.ruleRepository = ruleRepository;
        this.userRepository = userRepository;
        this.roleBasedAuthorityService = roleBasedAuthorityService;
    }

    /**
     * Get all role-authority rules.
     * @return List of all rules
     */
    public List<RoleAuthorityRule> getAllRules() {
        return ruleRepository.findAll();
    }

    /**
     * Get a rule by ID.
     * @param id The ID of the rule
     * @return The rule if found, or empty if not found
     */
    public Optional<RoleAuthorityRule> getRuleById(Long id) {
        return ruleRepository.findById(id);
    }

    /**
     * Get a rule by role name.
     * @param roleName The name of the role
     * @return The rule if found, or empty if not found
     */
    public Optional<RoleAuthorityRule> getRuleByRoleName(String roleName) {
        return ruleRepository.findByRoleName(roleName);
    }

    /**
     * Create or update a rule.
     * @param rule The rule to create or update
     * @return The created or updated rule
     */
    public RoleAuthorityRule saveRule(RoleAuthorityRule rule) {
        RoleAuthorityRule savedRule = ruleRepository.save(rule);
        
        // Update authorities for all users with this role
        updateAuthoritiesForUsersWithRole(rule.getRoleName());
        
        return savedRule;
    }

    /**
     * Delete a rule by ID.
     * @param id The ID of the rule to delete
     */
    public void deleteRule(Long id) {
        Optional<RoleAuthorityRule> ruleOpt = ruleRepository.findById(id);
        if (ruleOpt.isPresent()) {
            String roleName = ruleOpt.get().getRoleName();
            ruleRepository.deleteById(id);
            
            // Update authorities for all users with this role
            updateAuthoritiesForUsersWithRole(roleName);
        }
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