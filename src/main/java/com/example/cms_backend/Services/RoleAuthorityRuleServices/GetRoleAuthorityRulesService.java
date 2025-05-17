package com.example.cms_backend.Services.RoleAuthorityRuleServices;

import com.example.cms_backend.Abstractions.Query;
import com.example.cms_backend.Model.Entities.RoleAuthorityRule;
import com.example.cms_backend.Repositories.RoleAuthorityRuleRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Service to retrieve role-authority rules.
 */
@Service
public class GetRoleAuthorityRulesService implements Query<String, List<RoleAuthorityRule>> {
    private final RoleAuthorityRuleRepository ruleRepository;

    public GetRoleAuthorityRulesService(RoleAuthorityRuleRepository ruleRepository) {
        this.ruleRepository = ruleRepository;
    }

    /**
     * Execute the query to retrieve role-authority rules.
     * If the input is "all", returns all rules.
     * Otherwise, tries to find a rule by role name.
     * 
     * @param input "all" or a role name
     * @return ResponseEntity containing a list of rules or a single rule
     */
    @Override
    public ResponseEntity<List<RoleAuthorityRule>> execute(String input) {
        if ("all".equalsIgnoreCase(input)) {
            return ResponseEntity.ok(ruleRepository.findAll());
        } else {
            Optional<RoleAuthorityRule> ruleOpt = ruleRepository.findByRoleName(input);
            return ResponseEntity.ok(
                    ruleOpt.map(Collections::singletonList)
                            .orElse(Collections.emptyList())
            );
        }
    }
}