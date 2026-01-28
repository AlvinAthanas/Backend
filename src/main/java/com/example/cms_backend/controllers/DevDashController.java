package com.example.cms_backend.controllers;

import com.example.cms_backend.model.DTO.DeveloperDashboardStatsDTO;
import com.example.cms_backend.services.DevDashServices.GetDeveloperDashboardStatsService;
import com.example.cms_backend.services.DevDashServices.SendAdminVerificationKeyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/devdash")
public class DevDashController {

    private final GetDeveloperDashboardStatsService statsService;

    private final SendAdminVerificationKeyService sendAdminVerificationKeyService;

    public DevDashController(GetDeveloperDashboardStatsService statsService,
                             SendAdminVerificationKeyService sendAdminVerificationKeyService) {
        this.statsService = statsService;
        this.sendAdminVerificationKeyService = sendAdminVerificationKeyService;
    }


    @GetMapping("/stats")
    public ResponseEntity<DeveloperDashboardStatsDTO> getStats() {
        return statsService.execute(null);
    }

    @GetMapping("/send-key/{keyId}")
    public ResponseEntity<String> sendVerificationKeyEmail(@PathVariable Long keyId) {
        return sendAdminVerificationKeyService.execute(keyId);
    }

}
