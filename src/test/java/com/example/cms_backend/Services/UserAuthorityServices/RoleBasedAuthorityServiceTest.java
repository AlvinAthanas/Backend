package com.example.cms_backend.Services.UserAuthorityServices;

import com.example.cms_backend.Model.Entities.Role;
import com.example.cms_backend.Model.Entities.RoleAuthorityRule;
import com.example.cms_backend.Model.Entities.User;
import com.example.cms_backend.Model.Enums.Authority;
import com.example.cms_backend.Model.Enums.Roles;
import com.example.cms_backend.Repositories.RoleAuthorityRuleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RoleBasedAuthorityServiceTest {

    private RoleBasedAuthorityService roleBasedAuthorityService;
    private UpdateUserAuthoritiesService updateUserAuthoritiesService;
    private RoleAuthorityRuleRepository roleAuthorityRuleRepository;

    @BeforeEach
    public void setUp() {
        updateUserAuthoritiesService = Mockito.mock(UpdateUserAuthoritiesService.class);
        roleAuthorityRuleRepository = Mockito.mock(RoleAuthorityRuleRepository.class);
        roleBasedAuthorityService = new RoleBasedAuthorityService(updateUserAuthoritiesService, roleAuthorityRuleRepository);
    }

    @Test
    public void testUpdateAuthoritiesBasedOnRoles_WithCustomRule() {
        // Arrange
        User user = new User();
        user.setId(1L);

        // Create a role
        Role role = new Role(Roles.CATECHIST.toString());
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);

        // Create a custom rule for CATECHIST
        RoleAuthorityRule rule = new RoleAuthorityRule(Roles.CATECHIST.toString());
        rule.setOverrideDefault(true);
        Set<String> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(Authority.WRITE_MEMBERS.name());
        rule.setGrantedAuthorities(grantedAuthorities);

        // Mock the repository to return the custom rule
        when(roleAuthorityRuleRepository.findByRoleName(Roles.CATECHIST.toString()))
                .thenReturn(Optional.of(rule));

        // Act
        roleBasedAuthorityService.updateAuthoritiesBasedOnRoles(user);

        // Assert
        ArgumentCaptor<com.example.cms_backend.Model.Commands.UpdateUserAuthoritiesCommand> commandCaptor = 
                ArgumentCaptor.forClass(com.example.cms_backend.Model.Commands.UpdateUserAuthoritiesCommand.class);
        verify(updateUserAuthoritiesService).execute(commandCaptor.capture());

        com.example.cms_backend.Model.Commands.UpdateUserAuthoritiesCommand command = commandCaptor.getValue();
        assertEquals(1L, command.getId());
        assertEquals(1, command.getAuthorityNames().size());
        assertTrue(command.getAuthorityNames().contains(Authority.WRITE_MEMBERS.name()));
    }

    @Test
    public void testUpdateAuthoritiesBasedOnRoles_WithDefaultRules() {
        // Arrange
        User user = new User();
        user.setId(1L);

        // Create a role
        Role role = new Role(Roles.CATECHIST.toString());
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);

        // Mock the repository to return no custom rule
        when(roleAuthorityRuleRepository.findByRoleName(Roles.CATECHIST.toString()))
                .thenReturn(Optional.empty());

        // Act
        roleBasedAuthorityService.updateAuthoritiesBasedOnRoles(user);

        // Assert
        ArgumentCaptor<com.example.cms_backend.Model.Commands.UpdateUserAuthoritiesCommand> commandCaptor = 
                ArgumentCaptor.forClass(com.example.cms_backend.Model.Commands.UpdateUserAuthoritiesCommand.class);
        verify(updateUserAuthoritiesService).execute(commandCaptor.capture());

        com.example.cms_backend.Model.Commands.UpdateUserAuthoritiesCommand command = commandCaptor.getValue();
        assertEquals(1L, command.getId());

        // Should have all READ authorities plus WRITE_SACRAMENTS
        Set<String> expectedAuthorities = new HashSet<>();
        expectedAuthorities.add(Authority.READ_MEMBERS.name());
        expectedAuthorities.add(Authority.READ_CONTRIBUTIONS.name());
        expectedAuthorities.add(Authority.READ_SCHEDULES.name());
        expectedAuthorities.add(Authority.READ_EVENTS.name());
        expectedAuthorities.add(Authority.READ_COMMUNITIES.name());
        expectedAuthorities.add(Authority.READ_PROJECTS.name());
        expectedAuthorities.add(Authority.READ_SACRAMENTS.name());
        expectedAuthorities.add(Authority.WRITE_SACRAMENTS.name());

        assertEquals(expectedAuthorities.size(), command.getAuthorityNames().size());
        for (String authority : expectedAuthorities) {
            assertTrue(command.getAuthorityNames().contains(authority));
        }
    }

    @Test
    public void testUpdateAuthoritiesBasedOnRoles_WithMultipleCustomRules() {
        // Arrange
        User user = new User();
        user.setId(1L);

        // Create multiple roles
        Role catechistRole = new Role(Roles.CATECHIST.toString());
        Role communityChairpersonRole = new Role(Roles.COMMUNITY_CHAIRPERSON.toString());
        Set<Role> roles = new HashSet<>();
        roles.add(catechistRole);
        roles.add(communityChairpersonRole);
        user.setRoles(roles);

        // Create a custom rule for CATECHIST
        RoleAuthorityRule catechistRule = new RoleAuthorityRule(Roles.CATECHIST.toString());
        catechistRule.setOverrideDefault(true);
        Set<String> catechistGrantedAuthorities = new HashSet<>();
        catechistGrantedAuthorities.add(Authority.WRITE_MEMBERS.name());
        catechistRule.setGrantedAuthorities(catechistGrantedAuthorities);

        // Create a custom rule for COMMUNITY_CHAIRPERSON
        RoleAuthorityRule chairpersonRule = new RoleAuthorityRule(Roles.COMMUNITY_CHAIRPERSON.toString());
        chairpersonRule.setOverrideDefault(true);
        Set<String> chairpersonGrantedAuthorities = new HashSet<>();
        chairpersonGrantedAuthorities.add(Authority.WRITE_COMMUNITIES.name());
        chairpersonRule.setGrantedAuthorities(chairpersonGrantedAuthorities);

        // Mock the repository to return the custom rules
        when(roleAuthorityRuleRepository.findByRoleName(Roles.CATECHIST.toString()))
                .thenReturn(Optional.of(catechistRule));
        when(roleAuthorityRuleRepository.findByRoleName(Roles.COMMUNITY_CHAIRPERSON.toString()))
                .thenReturn(Optional.of(chairpersonRule));

        // Act
        roleBasedAuthorityService.updateAuthoritiesBasedOnRoles(user);

        // Assert
        ArgumentCaptor<com.example.cms_backend.Model.Commands.UpdateUserAuthoritiesCommand> commandCaptor = 
                ArgumentCaptor.forClass(com.example.cms_backend.Model.Commands.UpdateUserAuthoritiesCommand.class);
        verify(updateUserAuthoritiesService).execute(commandCaptor.capture());

        com.example.cms_backend.Model.Commands.UpdateUserAuthoritiesCommand command = commandCaptor.getValue();
        assertEquals(1L, command.getId());

        // Should have both WRITE_MEMBERS and WRITE_COMMUNITIES authorities
        Set<String> expectedAuthorities = new HashSet<>();
        expectedAuthorities.add(Authority.WRITE_MEMBERS.name());
        expectedAuthorities.add(Authority.WRITE_COMMUNITIES.name());

        assertEquals(expectedAuthorities.size(), command.getAuthorityNames().size());
        for (String authority : expectedAuthorities) {
            assertTrue(command.getAuthorityNames().contains(authority), "Authority " + authority + " should be present");
        }
    }

    @Test
    public void testUpdateAuthoritiesBasedOnRoles_WithOverrideDefaultFalse() {
        // Arrange
        User user = new User();
        user.setId(1L);

        // Create a role
        Role catechistRole = new Role(Roles.CATECHIST.toString());
        Set<Role> roles = new HashSet<>();
        roles.add(catechistRole);
        user.setRoles(roles);

        // Create a custom rule for CATECHIST with overrideDefault=false
        RoleAuthorityRule catechistRule = new RoleAuthorityRule(Roles.CATECHIST.toString());
        catechistRule.setOverrideDefault(false); // This is the key setting for this test

        // Add a granted authority
        Set<String> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(Authority.WRITE_MEMBERS.name());
        catechistRule.setGrantedAuthorities(grantedAuthorities);

        // Add a denied authority
        Set<String> deniedAuthorities = new HashSet<>();
        deniedAuthorities.add(Authority.READ_CONTRIBUTIONS.name());
        catechistRule.setDeniedAuthorities(deniedAuthorities);

        // Mock the repository to return the custom rule
        when(roleAuthorityRuleRepository.findByRoleName(Roles.CATECHIST.toString()))
                .thenReturn(Optional.of(catechistRule));

        // Act
        roleBasedAuthorityService.updateAuthoritiesBasedOnRoles(user);

        // Assert
        ArgumentCaptor<com.example.cms_backend.Model.Commands.UpdateUserAuthoritiesCommand> commandCaptor = 
                ArgumentCaptor.forClass(com.example.cms_backend.Model.Commands.UpdateUserAuthoritiesCommand.class);
        verify(updateUserAuthoritiesService).execute(commandCaptor.capture());

        com.example.cms_backend.Model.Commands.UpdateUserAuthoritiesCommand command = commandCaptor.getValue();
        assertEquals(1L, command.getId());

        // Should have all READ authorities except READ_CONTRIBUTIONS, plus WRITE_MEMBERS and WRITE_SACRAMENTS
        Set<String> expectedAuthorities = new HashSet<>();
        expectedAuthorities.add(Authority.READ_MEMBERS.name());
        expectedAuthorities.add(Authority.READ_SCHEDULES.name());
        expectedAuthorities.add(Authority.READ_EVENTS.name());
        expectedAuthorities.add(Authority.READ_COMMUNITIES.name());
        expectedAuthorities.add(Authority.READ_PROJECTS.name());
        expectedAuthorities.add(Authority.READ_SACRAMENTS.name());
        expectedAuthorities.add(Authority.WRITE_MEMBERS.name());
        expectedAuthorities.add(Authority.WRITE_SACRAMENTS.name());

        // Verify that all expected authorities are present
        for (String authority : expectedAuthorities) {
            assertTrue(command.getAuthorityNames().contains(authority), 
                    "Authority " + authority + " should be present");
        }

        // Verify that the denied authority is not present
        assertFalse(command.getAuthorityNames().contains(Authority.READ_CONTRIBUTIONS.name()), 
                "Authority READ_CONTRIBUTIONS should not be present");

        // Verify that the total number of authorities is correct
        assertEquals(expectedAuthorities.size(), command.getAuthorityNames().size());
    }
}
