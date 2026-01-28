package com.example.cms_backend.services.RoleAuthorityRuleServices;

import com.example.cms_backend.abstractions.Query;
import com.example.cms_backend.exceptions.AuthorityRuleNotFoundException;
import com.example.cms_backend.model.Entities.RoleAuthorityRule;
import com.example.cms_backend.repositories.RoleAuthorityRuleRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service to retrieve a role-authority rule by ID.
 */
@Service
public class GetRoleAuthorityRuleByIdService implements Query<Long, RoleAuthorityRule> {
    private final RoleAuthorityRuleRepository ruleRepository;

    public GetRoleAuthorityRuleByIdService(RoleAuthorityRuleRepository ruleRepository) {
        this.ruleRepository = ruleRepository;
    }

    /**
     * Execute the query to retrieve a role-authority rule by ID.
     * 
     * @param id The ID of the rule to retrieve
     * @return ResponseEntity containing the rule if found
     * @throws AuthorityRuleNotFoundException if the rule is not found
     */
    @Override
    public ResponseEntity<RoleAuthorityRule> execute(Long id) {
        Optional<RoleAuthorityRule> ruleOpt = ruleRepository.findById(id);
        if (ruleOpt.isEmpty()) {
            throw new AuthorityRuleNotFoundException(id);
        }
        return ResponseEntity.ok(ruleOpt.get());
    }
}
