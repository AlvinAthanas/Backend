package com.example.cms_backend.Controllers;

import com.example.cms_backend.Model.Commands.VerifyAdminKeyCommand;
import com.example.cms_backend.Model.DTO.AdminVerificationDetailsDTO;
import com.example.cms_backend.Model.DTO.UserDTO;
import com.example.cms_backend.Services.AdminServices.GetAllVerifiedParishionersOfParishService;
import com.example.cms_backend.Services.AdminVerificationKeyServices.GetAllAdminVerificationDetailsService;
import com.example.cms_backend.Services.AdminVerificationKeyServices.VerifyAdminKeyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AdminVerificationKeyController {

    private final VerifyAdminKeyService verifyAdminKeyService;
    private final GetAllVerifiedParishionersOfParishService getAllVerifiedParishionersOfParishService;
    private final GetAllAdminVerificationDetailsService adminVerificationDetailsService;

    public AdminVerificationKeyController(VerifyAdminKeyService verifyAdminKeyService,
                                          GetAllVerifiedParishionersOfParishService getAllVerifiedParishionersOfParishService,
                                          GetAllAdminVerificationDetailsService adminVerificationDetailsService) {
        this.verifyAdminKeyService = verifyAdminKeyService;
        this.getAllVerifiedParishionersOfParishService = getAllVerifiedParishionersOfParishService;
        this.adminVerificationDetailsService = adminVerificationDetailsService;
    }

    @PostMapping("/verify-key")
    public ResponseEntity<String> verifyKey(@RequestBody VerifyAdminKeyCommand command) {
        return verifyAdminKeyService.execute(command);
    }

    // Somewhere in an AdminController
    @GetMapping("/parish/{parishId}/verified-parishioners")
    public ResponseEntity<List<UserDTO>> getVerifiedParishioners(@PathVariable Long parishId) {
        return getAllVerifiedParishionersOfParishService.execute(parishId);
    }

    @GetMapping("/keys")
    public ResponseEntity<List<AdminVerificationDetailsDTO>> getAll(
            @RequestParam(required = false, defaultValue = "ALL") String status) {

        return adminVerificationDetailsService.execute(status);
    }

}
