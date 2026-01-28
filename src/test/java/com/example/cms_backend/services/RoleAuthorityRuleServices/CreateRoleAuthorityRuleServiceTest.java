package com.example.cms_backend.services.RoleAuthorityRuleServices;

import com.example.cms_backend.exceptions.DuplicateAuthorityException;
import com.example.cms_backend.exceptions.DuplicateRoleAuthorityRuleException;
import com.example.cms_backend.model.Commands.CreateRoleAuthorityRuleCommand;
import com.example.cms_backend.model.Entities.RoleAuthorityRule;
import com.example.cms_backend.model.Enums.Authority;
import com.example.cms_backend.repositories.RoleAuthorityRuleRepository;
import com.example.cms_backend.repositories.UserRepository;
import com.example.cms_backend.services.UserAuthorityServices.RoleBasedAuthorityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class CreateRoleAuthorityRuleServiceTest {

    @Mock
    private RoleAuthorityRuleRepository ruleRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleBasedAuthorityService roleBasedAuthorityService;

    private CreateRoleAuthorityRuleService service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new CreateRoleAuthorityRuleService(ruleRepository, userRepository, roleBasedAuthorityService);
        
        // Mock the repository to return a saved rule
        when(ruleRepository.save(any(RoleAuthorityRule.class))).thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    public void testExecute_Success() {
        // Arrange
        CreateRoleAuthorityRuleCommand command = new CreateRoleAuthorityRuleCommand();
        command.setRoleName("TEST_ROLE");
        command.setOverrideDefault(true);
        
        Set<String> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(Authority.READ_MEMBERS.name());
        command.setGrantedAuthorities(grantedAuthorities);
        
        Set<String> deniedAuthorities = new HashSet<>();
        deniedAuthorities.add(Authority.WRITE_MEMBERS.name());
        command.setDeniedAuthorities(deniedAuthorities);

        // Mock the repository to return false for existsByRoleName
        when(ruleRepository.existsByRoleName("TEST_ROLE")).thenReturn(false);

        // Act
        ResponseEntity<RoleAuthorityRule> response = service.execute(command);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("TEST_ROLE", response.getBody().getRoleName());
        assertTrue(response.getBody().isOverrideDefault());
        assertEquals(1, response.getBody().getGrantedAuthorities().size());
        assertTrue(response.getBody().getGrantedAuthorities().contains(Authority.READ_MEMBERS.name()));
        assertEquals(1, response.getBody().getDeniedAuthorities().size());
        assertTrue(response.getBody().getDeniedAuthorities().contains(Authority.WRITE_MEMBERS.name()));
    }

    @Test
    public void testExecute_DuplicateAuthority() {
        // Arrange
        CreateRoleAuthorityRuleCommand command = new CreateRoleAuthorityRuleCommand();
        command.setRoleName("TEST_ROLE");
        
        Set<String> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(Authority.READ_MEMBERS.name());
        command.setGrantedAuthorities(grantedAuthorities);
        
        Set<String> deniedAuthorities = new HashSet<>();
        deniedAuthorities.add(Authority.READ_MEMBERS.name()); // Same authority as in granted
        command.setDeniedAuthorities(deniedAuthorities);

        // Mock the repository to return false for existsByRoleName
        when(ruleRepository.existsByRoleName("TEST_ROLE")).thenReturn(false);

        // Act & Assert
        DuplicateAuthorityException exception = assertThrows(
            DuplicateAuthorityException.class,
            () -> service.execute(command)
        );
        
        assertEquals("Authority 'READ_MEMBERS' cannot be both granted and denied", exception.getMessage());
    }

    @Test
    public void testExecute_DuplicateRoleName() {
        // Arrange
        CreateRoleAuthorityRuleCommand command = new CreateRoleAuthorityRuleCommand();
        command.setRoleName("EXISTING_ROLE");
        
        // Mock the repository to return true for existsByRoleName
        when(ruleRepository.existsByRoleName("EXISTING_ROLE")).thenReturn(true);

        // Act & Assert
        DuplicateRoleAuthorityRuleException exception = assertThrows(
            DuplicateRoleAuthorityRuleException.class,
            () -> service.execute(command)
        );
        
        assertEquals("Authority rule already exists for role: EXISTING_ROLE", exception.getMessage());
    }
}