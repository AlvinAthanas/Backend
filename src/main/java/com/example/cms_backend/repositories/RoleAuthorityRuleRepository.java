package com.example.cms_backend.repositories;

import com.example.cms_backend.model.Entities.RoleAuthorityRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for managing RoleAuthorityRule entities.
 */
@Repository
public interface RoleAuthorityRuleRepository extends JpaRepository<RoleAuthorityRule, Long> {
    
    /**
     * Find a rule by role name.
     * @param roleName The name of the role
     * @return An Optional containing the rule if found, or empty if not found
     */
    Optional<RoleAuthorityRule> findByRoleName(String roleName);
    
    /**
     * Check if a rule exists for a role.
     * @param roleName The name of the role
     * @return True if a rule exists, false otherwise
     */
    boolean existsByRoleName(String roleName);
}