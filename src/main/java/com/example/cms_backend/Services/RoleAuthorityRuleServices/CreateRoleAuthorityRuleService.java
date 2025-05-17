package com.example.cms_backend.Services.RoleAuthorityRuleServices;

import com.example.cms_backend.Abstractions.Command;
import com.example.cms_backend.Exceptions.DuplicateAuthorityException;
import com.example.cms_backend.Exceptions.DuplicateRoleAuthorityRuleException;
import com.example.cms_backend.Model.Commands.CreateRoleAuthorityRuleCommand;
import com.example.cms_backend.Model.Entities.RoleAuthorityRule;
import com.example.cms_backend.Model.Entities.User;
import com.example.cms_backend.Repositories.RoleAuthorityRuleRepository;
import com.example.cms_backend.Repositories.UserRepository;
import com.example.cms_backend.Services.UserAuthorityServices.RoleBasedAuthorityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * Service to create a new role-authority rule.
 */
@Service
public class CreateRoleAuthorityRuleService implements Command<CreateRoleAuthorityRuleCommand, RoleAuthorityRule> {
    private final RoleAuthorityRuleRepository ruleRepository;
    private final UserRepository userRepository;
    private final RoleBasedAuthorityService roleBasedAuthorityService;

    public CreateRoleAuthorityRuleService(RoleAuthorityRuleRepository ruleRepository,
                                         UserRepository userRepository,
                                         RoleBasedAuthorityService roleBasedAuthorityService) {
        this.ruleRepository = ruleRepository;
        this.userRepository = userRepository;
        this.roleBasedAuthorityService = roleBasedAuthorityService;
    }

    /**
     * Execute the command to create a new role-authority rule.
     * 
     * @param command The command containing the rule details
     * @return ResponseEntity containing the created rule
     */
    @Override
    public ResponseEntity<RoleAuthorityRule> execute(CreateRoleAuthorityRuleCommand command) {
        // Check if a rule with the same role name already exists
        if (ruleRepository.existsByRoleName(command.getRoleName())) {
            throw new DuplicateRoleAuthorityRuleException(command.getRoleName());
        }

        // Check for duplicate authorities in granted and denied sets
        checkForDuplicateAuthorities(command.getGrantedAuthorities(), command.getDeniedAuthorities());

        RoleAuthorityRule rule = new RoleAuthorityRule(command.getRoleName());
        rule.setOverrideDefault(command.isOverrideDefault());
        rule.setGrantedAuthorities(command.getGrantedAuthorities());
        rule.setDeniedAuthorities(command.getDeniedAuthorities());

        RoleAuthorityRule savedRule = ruleRepository.save(rule);

        // Update authorities for all users with this role
        updateAuthoritiesForUsersWithRole(rule.getRoleName());

        return ResponseEntity.status(HttpStatus.CREATED).body(savedRule);
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

    /**
     * Check if there are any duplicate authorities between granted and denied sets.
     * @param grantedAuthorities The set of granted authorities
     * @param deniedAuthorities The set of denied authorities
     * @throws DuplicateAuthorityException if any authority appears in both sets
     */
    private void checkForDuplicateAuthorities(Set<String> grantedAuthorities, Set<String> deniedAuthorities) {
        for (String authority : grantedAuthorities) {
            if (deniedAuthorities.contains(authority)) {
                throw new DuplicateAuthorityException(authority);
            }
        }
    }
}
