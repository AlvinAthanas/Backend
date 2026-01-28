package com.example.cms_backend.model.Entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

/**
 * Entity to store custom role-authority rules.
 * This allows for configuring specific authorities for roles,
 * overriding the default rules in RoleBasedAuthorityService.
 */
@Entity
@Table(name = "role_authority_rule")
@Data
@NoArgsConstructor
public class RoleAuthorityRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String roleName;
    
    /**
     * If true, this rule completely replaces the default authorities for the role.
     * If false, this rule adds to or removes from the default authorities.
     */
    private boolean overrideDefault = true;
    
    /**
     * The set of authorities to grant for this role.
     * If overrideDefault is true, only these authorities will be granted.
     * If overrideDefault is false, these authorities will be added to the default ones.
     */
    @ElementCollection
    @CollectionTable(name = "role_authority_rule_granted", 
                    joinColumns = @JoinColumn(name = "rule_id"))
    @Column(name = "authority_name")
    private Set<String> grantedAuthorities = new HashSet<>();
    
    /**
     * The set of authorities to deny for this role.
     * These authorities will be removed from the granted authorities.
     * Only used when overrideDefault is false.
     */
    @ElementCollection
    @CollectionTable(name = "role_authority_rule_denied", 
                    joinColumns = @JoinColumn(name = "rule_id"))
    @Column(name = "authority_name")
    private Set<String> deniedAuthorities = new HashSet<>();
    
    public RoleAuthorityRule(String roleName) {
        this.roleName = roleName;
    }
}