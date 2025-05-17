package com.example.cms_backend.Services.RoleAuthorityRuleServices;

import com.example.cms_backend.Abstractions.Query;
import com.example.cms_backend.Exceptions.AuthorityRuleNotFoundException;
import com.example.cms_backend.Model.Entities.RoleAuthorityRule;
import com.example.cms_backend.Repositories.RoleAuthorityRuleRepository;
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
