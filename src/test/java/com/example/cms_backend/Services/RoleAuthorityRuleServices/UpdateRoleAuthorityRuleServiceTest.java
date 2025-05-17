package com.example.cms_backend.Services.RoleAuthorityRuleServices;

import com.example.cms_backend.Exceptions.AuthorityRuleNotFoundException;
import com.example.cms_backend.Exceptions.DuplicateAuthorityException;
import com.example.cms_backend.Model.Commands.CreateRoleAuthorityRuleCommand;
import com.example.cms_backend.Model.Entities.RoleAuthorityRule;
import com.example.cms_backend.Model.Enums.Authority;
import com.example.cms_backend.Repositories.RoleAuthorityRuleRepository;
import com.example.cms_backend.Repositories.UserRepository;
import com.example.cms_backend.Services.UserAuthorityServices.RoleBasedAuthorityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class UpdateRoleAuthorityRuleServiceTest {

    @Mock
    private RoleAuthorityRuleRepository ruleRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleBasedAuthorityService roleBasedAuthorityService;

    private UpdateRoleAuthorityRuleService service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new UpdateRoleAuthorityRuleService(ruleRepository, userRepository, roleBasedAuthorityService);
        
        // Mock the repository to return a saved rule
        when(ruleRepository.save(any(RoleAuthorityRule.class))).thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    public void testExecute_Success() {
        // Arrange
        Long ruleId = 1L;
        RoleAuthorityRule existingRule = new RoleAuthorityRule("OLD_ROLE");
        existingRule.setId(ruleId);
        
        CreateRoleAuthorityRuleCommand command = new CreateRoleAuthorityRuleCommand();
        command.setRoleName("NEW_ROLE");
        command.setOverrideDefault(true);
        
        Set<String> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(Authority.READ_MEMBERS.name());
        command.setGrantedAuthorities(grantedAuthorities);
        
        Set<String> deniedAuthorities = new HashSet<>();
        deniedAuthorities.add(Authority.WRITE_MEMBERS.name());
        command.setDeniedAuthorities(deniedAuthorities);
        
        UpdateRoleAuthorityRuleService.UpdateCommand updateCommand = 
                new UpdateRoleAuthorityRuleService.UpdateCommand(ruleId, command);
        
        when(ruleRepository.findById(ruleId)).thenReturn(Optional.of(existingRule));

        // Act
        ResponseEntity<RoleAuthorityRule> response = service.execute(updateCommand);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("NEW_ROLE", response.getBody().getRoleName());
        assertTrue(response.getBody().isOverrideDefault());
        assertEquals(1, response.getBody().getGrantedAuthorities().size());
        assertTrue(response.getBody().getGrantedAuthorities().contains(Authority.READ_MEMBERS.name()));
        assertEquals(1, response.getBody().getDeniedAuthorities().size());
        assertTrue(response.getBody().getDeniedAuthorities().contains(Authority.WRITE_MEMBERS.name()));
    }

    @Test
    public void testExecute_DuplicateAuthority() {
        // Arrange
        Long ruleId = 1L;
        RoleAuthorityRule existingRule = new RoleAuthorityRule("TEST_ROLE");
        existingRule.setId(ruleId);
        
        CreateRoleAuthorityRuleCommand command = new CreateRoleAuthorityRuleCommand();
        command.setRoleName("TEST_ROLE");
        
        Set<String> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(Authority.READ_MEMBERS.name());
        command.setGrantedAuthorities(grantedAuthorities);
        
        Set<String> deniedAuthorities = new HashSet<>();
        deniedAuthorities.add(Authority.READ_MEMBERS.name()); // Same authority as in granted
        command.setDeniedAuthorities(deniedAuthorities);
        
        UpdateRoleAuthorityRuleService.UpdateCommand updateCommand = 
                new UpdateRoleAuthorityRuleService.UpdateCommand(ruleId, command);
        
        when(ruleRepository.findById(ruleId)).thenReturn(Optional.of(existingRule));

        // Act & Assert
        DuplicateAuthorityException exception = assertThrows(
            DuplicateAuthorityException.class,
            () -> service.execute(updateCommand)
        );
        
        assertEquals("Authority 'READ_MEMBERS' cannot be both granted and denied", exception.getMessage());
    }

    @Test
    public void testExecute_RuleNotFound() {
        // Arrange
        Long ruleId = 1L;
        
        CreateRoleAuthorityRuleCommand command = new CreateRoleAuthorityRuleCommand();
        command.setRoleName("TEST_ROLE");
        
        UpdateRoleAuthorityRuleService.UpdateCommand updateCommand = 
                new UpdateRoleAuthorityRuleService.UpdateCommand(ruleId, command);
        
        when(ruleRepository.findById(ruleId)).thenReturn(Optional.empty());

        // Act & Assert
        AuthorityRuleNotFoundException exception = assertThrows(
            AuthorityRuleNotFoundException.class,
            () -> service.execute(updateCommand)
        );
        
        assertEquals("Authority rule not found with id: 1", exception.getMessage());
    }
}