package com.example.cms_backend.services.DevDashServices;

import com.example.cms_backend.abstractions.Query;
import com.example.cms_backend.model.DTO.DeveloperDashboardStatsDTO;
import com.example.cms_backend.model.Entities.User;
import com.example.cms_backend.model.Enums.AdminVerificationStatus;
import com.example.cms_backend.repositories.AdminVerificationKeyRepository;
import com.example.cms_backend.repositories.ParishRepository;
import com.example.cms_backend.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;

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
