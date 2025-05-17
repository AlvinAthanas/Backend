package com.example.cms_backend.Services.UserAuthorityServices;

import com.example.cms_backend.Model.Commands.UpdateUserAuthoritiesCommand;
import com.example.cms_backend.Model.Entities.Role;
import com.example.cms_backend.Model.Entities.User;
import com.example.cms_backend.Model.Enums.Authority;
import com.example.cms_backend.Model.Enums.Roles;
import org.springframework.stereotype.Service;

import java.util.HashSet;
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

    public RoleBasedAuthorityService(UpdateUserAuthoritiesService updateUserAuthoritiesService) {
        this.updateUserAuthoritiesService = updateUserAuthoritiesService;
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