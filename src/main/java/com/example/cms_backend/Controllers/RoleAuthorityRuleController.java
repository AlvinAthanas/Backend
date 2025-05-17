package com.example.cms_backend.Controllers;

import com.example.cms_backend.Model.Commands.CreateRoleAuthorityRuleCommand;
import com.example.cms_backend.Model.Entities.RoleAuthorityRule;
import com.example.cms_backend.Services.RoleAuthorityRuleServices.CreateRoleAuthorityRuleService;
import com.example.cms_backend.Services.RoleAuthorityRuleServices.DeleteRoleAuthorityRuleService;
import com.example.cms_backend.Services.RoleAuthorityRuleServices.GetRoleAuthorityRuleByIdService;
import com.example.cms_backend.Services.RoleAuthorityRuleServices.GetRoleAuthorityRuleByRoleNameService;
import com.example.cms_backend.Services.RoleAuthorityRuleServices.GetRoleAuthorityRulesService;
import com.example.cms_backend.Services.RoleAuthorityRuleServices.UpdateRoleAuthorityRuleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing role-authority rule configurations.
 */
@RestController
@RequestMapping("/role-authority-rules")
public class RoleAuthorityRuleController {
    private final GetRoleAuthorityRulesService getRulesService;
    private final GetRoleAuthorityRuleByIdService getRuleByIdService;
    private final GetRoleAuthorityRuleByRoleNameService getRuleByRoleNameService;
    private final CreateRoleAuthorityRuleService createRuleService;
    private final UpdateRoleAuthorityRuleService updateRuleService;
    private final DeleteRoleAuthorityRuleService deleteRuleService;

    public RoleAuthorityRuleController(
            GetRoleAuthorityRulesService getRulesService,
            GetRoleAuthorityRuleByIdService getRuleByIdService,
            GetRoleAuthorityRuleByRoleNameService getRuleByRoleNameService,
            CreateRoleAuthorityRuleService createRuleService,
            UpdateRoleAuthorityRuleService updateRuleService,
            DeleteRoleAuthorityRuleService deleteRuleService) {
        this.getRulesService = getRulesService;
        this.getRuleByIdService = getRuleByIdService;
        this.getRuleByRoleNameService = getRuleByRoleNameService;
        this.createRuleService = createRuleService;
        this.updateRuleService = updateRuleService;
        this.deleteRuleService = deleteRuleService;
    }

    /**
     * Get all role-authority rules.
     * @return List of all rules
     */
    @GetMapping("/AuthorityRules")
    public ResponseEntity<List<RoleAuthorityRule>> getAllRules() {
        return getRulesService.execute("all");
    }

    /**
     * Get a rule by ID.
     * @param id The ID of the rule
     * @return The rule if found, or 404 if not found
     */
    @GetMapping("/AuthorityRule/{id}")
    public ResponseEntity<RoleAuthorityRule> getRuleById(@PathVariable Long id) {
        return getRuleByIdService.execute(id);
    }

    /**
     * Get a rule by role name.
     * @param roleName The name of the role
     * @return The rule if found, or a default rule if not found
     */
    @GetMapping("/AuthorityRule/role/{roleName}")
    public ResponseEntity<RoleAuthorityRule> getRuleByRoleName(@PathVariable String roleName) {
        return getRuleByRoleNameService.execute(roleName);
    }

    /**
     * Create a new rule.
     * @param command The command to create the rule
     * @return The created rule
     */
    @PostMapping("/AuthorityRule")
    public ResponseEntity<RoleAuthorityRule> createRule(@RequestBody CreateRoleAuthorityRuleCommand command) {
        return createRuleService.execute(command);
    }

    /**
     * Update an existing rule.
     * @param id The ID of the rule to update
     * @param command The command to update the rule
     * @return The updated rule if found, or 404 if not found
     */
    @PutMapping("AuthorityRule/{id}")
    public ResponseEntity<RoleAuthorityRule> updateRule(@PathVariable Long id, 
                                                      @RequestBody CreateRoleAuthorityRuleCommand command) {
        UpdateRoleAuthorityRuleService.UpdateCommand updateCommand = 
                new UpdateRoleAuthorityRuleService.UpdateCommand(id, command);
        return updateRuleService.execute(updateCommand);
    }

    /**
     * Delete a rule by ID.
     * @param id The ID of the rule to delete
     * @return 204 No Content if successful, or 404 if not found
     */
    @DeleteMapping("AuthorityRule/{id}")
    public ResponseEntity<Void> deleteRule(@PathVariable Long id) {
        return deleteRuleService.execute(id);
    }
}
