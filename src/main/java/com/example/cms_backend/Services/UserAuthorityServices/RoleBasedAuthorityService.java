package com.example.cms_backend.Services.UserAuthorityServices;

import com.example.cms_backend.Model.Commands.UpdateUserAuthoritiesCommand;
import com.example.cms_backend.Model.Entities.Role;
import com.example.cms_backend.Model.Entities.RoleAuthorityRule;
import com.example.cms_backend.Model.Entities.User;
import com.example.cms_backend.Model.Enums.Authority;
import com.example.cms_backend.Model.Enums.Roles;
import com.example.cms_backend.Repositories.RoleAuthorityRuleRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Service to manage role-based authority assignments.
 * This service implements the following rules:
 * 1. Users with only a PARISH_MEMBER role should have no authorities
 * 2. Users with community roles or CATECHIST should have only READ authorities (unless they have higher roles)
 * 3. Users with committee roles or PARISHIONER should have all authorities
 */
@Service
public class RoleBasedAuthorityService {
    private final UpdateUserAuthoritiesService updateUserAuthoritiesService;
    private final RoleAuthorityRuleRepository roleAuthorityRuleRepository;

    public RoleBasedAuthorityService(UpdateUserAuthoritiesService updateUserAuthoritiesService,
                                    RoleAuthorityRuleRepository roleAuthorityRuleRepository) {
        this.updateUserAuthoritiesService = updateUserAuthoritiesService;
        this.roleAuthorityRuleRepository = roleAuthorityRuleRepository;
    }

    /**
     * Updates a user's authorities based on their roles.
     * @param user The user whose authorities should be updated
     */
    public void updateAuthoritiesBasedOnRoles(User user) {
        Set<String> roleNames = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet());

        Set<String> authorityNames = determineAuthoritiesFromRoles(roleNames);

        UpdateUserAuthoritiesCommand command = new UpdateUserAuthoritiesCommand(user.getId(), authorityNames);
        updateUserAuthoritiesService.execute(command);
    }

    /**
     * Determines which authorities a user should have based on their roles.
     * @param roleNames The names of the roles the user has
     * @return The names of the authorities the user should have
     */
    private Set<String> determineAuthoritiesFromRoles(Set<String> roleNames) {
        // Check if there are any custom rules for the roles
        Set<String> customAuthorities = new HashSet<>();
        boolean hasCustomRules = false;

        // Process each role to check for custom rules
        for (String roleName : roleNames) {
            Optional<RoleAuthorityRule> ruleOpt = roleAuthorityRuleRepository.findByRoleName(roleName);
            if (ruleOpt.isPresent()) {
                hasCustomRules = true;
                RoleAuthorityRule rule = ruleOpt.get();

                // Apply the custom rule
                Set<String> roleAuthorities = new HashSet<>();
                applyCustomRule(rule, roleAuthorities);

                // Combine with existing authorities
                customAuthorities.addAll(roleAuthorities);
            }
        }

        // If there are custom rules, return the custom authorities
        if (hasCustomRules) {
            return customAuthorities;
        }

        // Otherwise, apply the default rules

        // If a user has only a PARISH_MEMBER role, they should have no authorities
        if (roleNames.size() == 1 && roleNames.contains(Roles.PARISH_MEMBER.toString())) {
            return new HashSet<>();
        }

        // If a user has committee roles or PARISHIONER, they should have all authorities
        if (hasCommitteeRolesOrParishioner(roleNames)) {
            return getAllAuthorities();
        }

        // If user has community roles or CATECHIST, they should have only READ authorities
        if (hasCommunityRolesOrCatechist(roleNames)) {
            return getReadAuthorities();
        }

        // Default: no authorities
        return new HashSet<>();
    }

    /**
     * Applies a custom rule to the set of authorities.
     * @param rule The custom rule to apply
     * @param authorities The set of authorities to modify
     */
    private void applyCustomRule(RoleAuthorityRule rule, Set<String> authorities) {
        if (rule.isOverrideDefault()) {
            // If overrideDefault is true, replace all authorities with the granted ones
            authorities.addAll(rule.getGrantedAuthorities());
        } else {
            // If overrideDefault is false, add the granted authorities and remove the denied ones

            // First, get the default authorities for the role
            Set<String> defaultAuthorities = getDefaultAuthoritiesForRole(rule.getRoleName());

            // Add the default authorities
            authorities.addAll(defaultAuthorities);

            // Add the granted authorities
            authorities.addAll(rule.getGrantedAuthorities());

            // Remove the denied authorities
            authorities.removeAll(rule.getDeniedAuthorities());
        }
    }

    /**
     * Gets the default authorities for a role based on the default rules.
     * @param roleName The name of the role
     * @return The default authorities for the role
     */
    private Set<String> getDefaultAuthoritiesForRole(String roleName) {
        Set<String> roleNames = new HashSet<>();
        roleNames.add(roleName);

        // Apply the default rules
        if (roleName.equals(Roles.PARISH_MEMBER.toString())) {
            return new HashSet<>();
        } else if (hasCommitteeRolesOrParishioner(roleNames)) {
            return getAllAuthorities();
        } else if (hasCommunityRolesOrCatechist(roleNames)) {
            return getReadAuthorities();
        } else {
            return new HashSet<>();
        }
    }

    /**
     * Public method to get the default authorities for a role.
     * This is used by other services that need to know the default authorities for a role.
     * @param roleName The name of the role
     * @return The default authorities for the role
     */
    public Set<String> getDefaultAuthoritiesForRoleName(String roleName) {
        return getDefaultAuthoritiesForRole(roleName);
    }

    /**
     * Creates a default RoleAuthorityRule for a role.
     * This is used when no custom rule exists for a role.
     * @param roleName The name of the role
     * @return A RoleAuthorityRule with default authorities
     */
    public RoleAuthorityRule createDefaultRuleForRole(String roleName) {
        RoleAuthorityRule rule = new RoleAuthorityRule(roleName);
        rule.setOverrideDefault(true);
        rule.setGrantedAuthorities(getDefaultAuthoritiesForRole(roleName));
        return rule;
    }

    /**
     * Checks if the user has any committee roles or PARISHIONER role.
     * @param roleNames The names of the roles the user has
     * @return True if the user has any committee roles or PARISHIONER role, false otherwise
     */
    private boolean hasCommitteeRolesOrParishioner(Set<String> roleNames) {
        return roleNames.contains(Roles.COMMITTEE_CHAIRPERSON.toString()) ||
               roleNames.contains(Roles.COMMITTEE_SECRETARY.toString()) ||
               roleNames.contains(Roles.COMMITTEE_TREASURER.toString()) ||
               roleNames.contains(Roles.PARISHIONER.toString());
    }

    /**
     * Checks if the user has any community roles or CATECHIST role.
     * @param roleNames The names of the roles the user has
     * @return True if the user has any community roles or CATECHIST role, false otherwise
     */
    private boolean hasCommunityRolesOrCatechist(Set<String> roleNames) {
        return roleNames.contains(Roles.COMMUNITY_CHAIRPERSON.toString()) ||
               roleNames.contains(Roles.COMMUNITY_SECRETARY.toString()) ||
               roleNames.contains(Roles.COMMUNITY_TREASURER.toString()) ||
               roleNames.contains(Roles.CATECHIST.toString());
    }

    /**
     * Gets all READ authorities.
     * @return A set of all READ authority names
     */
    private Set<String> getReadAuthorities() {
        return Stream.of(
                Authority.READ_MEMBERS.name(),
                Authority.READ_CONTRIBUTIONS.name(),
                Authority.READ_SCHEDULES.name(),
                Authority.READ_EVENTS.name(),
                Authority.READ_COMMUNITIES.name(),
                Authority.READ_PROJECTS.name(),
                Authority.READ_SACRAMENTS.name()
        ).collect(Collectors.toSet());
    }

    /**
     * Gets all authorities (both READ and WRITE).
     * @return A set of all authority names
     */
    private Set<String> getAllAuthorities() {
        return Stream.of(Authority.values())
                .map(Enum::name)
                .collect(Collectors.toSet());
    }
}
