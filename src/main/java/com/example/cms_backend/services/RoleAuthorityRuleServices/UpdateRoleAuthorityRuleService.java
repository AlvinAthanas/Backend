package com.example.cms_backend.services.RoleAuthorityRuleServices;

import com.example.cms_backend.abstractions.Command;
import com.example.cms_backend.exceptions.AuthorityRuleNotFoundException;
import com.example.cms_backend.exceptions.DuplicateAuthorityException;
import com.example.cms_backend.model.Commands.CreateRoleAuthorityRuleCommand;
import com.example.cms_backend.model.Entities.RoleAuthorityRule;
import com.example.cms_backend.model.Entities.User;
import com.example.cms_backend.repositories.RoleAuthorityRuleRepository;
import com.example.cms_backend.repositories.UserRepository;
import com.example.cms_backend.services.UserAuthorityServices.RoleBasedAuthorityService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Service to update an existing role-authority rule.
 */
@Service
public class UpdateRoleAuthorityRuleService implements Command<UpdateRoleAuthorityRuleService.UpdateCommand, RoleAuthorityRule> {
    private final RoleAuthorityRuleRepository ruleRepository;
    private final UserRepository userRepository;
    private final RoleBasedAuthorityService roleBasedAuthorityService;

    public UpdateRoleAuthorityRuleService(RoleAuthorityRuleRepository ruleRepository,
                                         UserRepository userRepository,
                                         RoleBasedAuthorityService roleBasedAuthorityService) {
        this.ruleRepository = ruleRepository;
        this.userRepository = userRepository;
        this.roleBasedAuthorityService = roleBasedAuthorityService;
    }

    /**
     * Execute the command to update an existing role-authority rule.
     * 
     * @param command The command containing the rule ID and updated details
     * @return ResponseEntity containing the updated rule if found, or 404 if not found
     */
    @Override
    public ResponseEntity<RoleAuthorityRule> execute(UpdateCommand command) {
        Optional<RoleAuthorityRule> ruleOpt = ruleRepository.findById(command.getId());
        if (ruleOpt.isEmpty()) {
            throw new AuthorityRuleNotFoundException(command.getId());
        }

        // Check for duplicate authorities in granted and denied sets
        checkForDuplicateAuthorities(
            command.getCommand().getGrantedAuthorities(), 
            command.getCommand().getDeniedAuthorities()
        );

        RoleAuthorityRule rule = ruleOpt.get();
        String oldRoleName = rule.getRoleName();

        rule.setRoleName(command.getCommand().getRoleName());
        rule.setOverrideDefault(command.getCommand().isOverrideDefault());
        rule.setGrantedAuthorities(command.getCommand().getGrantedAuthorities());
        rule.setDeniedAuthorities(command.getCommand().getDeniedAuthorities());

        RoleAuthorityRule savedRule = ruleRepository.save(rule);

        // Update authorities for users with the old role name (if it changed)
        if (!oldRoleName.equals(rule.getRoleName())) {
            updateAuthoritiesForUsersWithRole(oldRoleName);
        }

        // Update authorities for users with the new role name
        updateAuthoritiesForUsersWithRole(rule.getRoleName());

        return ResponseEntity.ok(savedRule);
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

    /**
     * Command class for updating a role-authority rule.
     */
    public static class UpdateCommand {
        private final Long id;
        private final CreateRoleAuthorityRuleCommand command;

        public UpdateCommand(Long id, CreateRoleAuthorityRuleCommand command) {
            this.id = id;
            this.command = command;
        }

        public Long getId() {
            return id;
        }

        public CreateRoleAuthorityRuleCommand getCommand() {
            return command;
        }
    }
}
