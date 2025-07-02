package com.example.cms_backend.Services.DevDashServices;

import com.example.cms_backend.Abstractions.Query;
import com.example.cms_backend.Model.DTO.DeveloperDashboardStatsDTO;
import com.example.cms_backend.Model.Entities.User;
import com.example.cms_backend.Model.Enums.AdminVerificationStatus;
import com.example.cms_backend.Repositories.AdminVerificationKeyRepository;
import com.example.cms_backend.Repositories.ParishRepository;
import com.example.cms_backend.Repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class GetDeveloperDashboardStatsService implements Query<Void, DeveloperDashboardStatsDTO> {

    private final ParishRepository parishRepo;
    private final UserRepository userRepo;
    private final AdminVerificationKeyRepository keyRepo;

    public GetDeveloperDashboardStatsService(
            ParishRepository parishRepo,
            UserRepository userRepo,
            AdminVerificationKeyRepository keyRepo
    ) {
        this.parishRepo = parishRepo;
        this.userRepo = userRepo;
        this.keyRepo = keyRepo;
    }

    @Override
    public ResponseEntity<DeveloperDashboardStatsDTO> execute(Void input) {
        long totalParishes = parishRepo.count();

        // Get unique parish IDs for VERIFIED admins
        var verifiedParishIds = userRepo.findByAdminVerificationStatus(AdminVerificationStatus.VERIFIED).stream()
                .map(User::getParishId)
                .filter(Objects::nonNull)
                .distinct()
                .toList();

        long verifiedParishes = verifiedParishIds.size();

        long pendingAdminVerifications = keyRepo.findAll().stream()
                .filter(key -> !key.isUsed())
                .count();

        long totalUsers = userRepo.count();

        DeveloperDashboardStatsDTO stats = new DeveloperDashboardStatsDTO(
                totalParishes,
                verifiedParishes,
                pendingAdminVerifications,
                totalUsers
        );

        return ResponseEntity.ok(stats);
    }
}
