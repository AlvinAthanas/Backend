package com.example.cms_backend.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeveloperDashboardStatsDTO {
    private long totalParishes;
    private long verifiedParishes;
    private long pendingAdminVerifications;
    private long totalUsers;
}
