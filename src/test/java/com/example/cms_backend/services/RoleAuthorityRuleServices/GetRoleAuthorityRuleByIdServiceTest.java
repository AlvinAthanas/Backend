package com.example.cms_backend.services.RoleAuthorityRuleServices;

import com.example.cms_backend.exceptions.AuthorityRuleNotFoundException;
import com.example.cms_backend.model.Entities.RoleAuthorityRule;
import com.example.cms_backend.repositories.RoleAuthorityRuleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class GetRoleAuthorityRuleByIdServiceTest {

    @Mock
    private RoleAuthorityRuleRepository ruleRepository;

    private GetRoleAuthorityRuleByIdService service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new GetRoleAuthorityRuleByIdService(ruleRepository);
    }

    @Test
    public void testExecute_Success() {
        // Arrange
        Long ruleId = 1L;
        RoleAuthorityRule rule = new RoleAuthorityRule("TEST_ROLE");
        rule.setId(ruleId);
        
        when(ruleRepository.findById(ruleId)).thenReturn(Optional.of(rule));

        // Act
        ResponseEntity<RoleAuthorityRule> response = service.execute(ruleId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("TEST_ROLE", response.getBody().getRoleName());
    }

    @Test
    public void testExecute_RuleNotFound() {
        // Arrange
        Long ruleId = 1L;
        
        when(ruleRepository.findById(ruleId)).thenReturn(Optional.empty());

        // Act & Assert
        AuthorityRuleNotFoundException exception = assertThrows(
            AuthorityRuleNotFoundException.class,
            () -> service.execute(ruleId)
        );
        
        assertEquals("Authority rule not found with id: 1", exception.getMessage());
    }
}