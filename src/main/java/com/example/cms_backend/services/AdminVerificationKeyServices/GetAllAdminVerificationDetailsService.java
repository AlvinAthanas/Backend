package com.example.cms_backend.services.AdminVerificationKeyServices;

import com.example.cms_backend.abstractions.Query;
import com.example.cms_backend.model.DTO.AdminVerificationDetailsDTO;
import com.example.cms_backend.model.DTO.AdminVerificationFilterDTO;
import com.example.cms_backend.model.Entities.AdminVerificationKey;
import com.example.cms_backend.model.Enums.AdminVerificationStatus;
import com.example.cms_backend.repositories.AdminVerificationKeyRepository;
import com.example.cms_backend.repositories.ParishRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetAllAdminVerificationDetailsService
        implements Query<AdminVerificationFilterDTO, List<AdminVerificationDetailsDTO>> {

    private final AdminVerificationKeyRepository keyRepo;
    private final ParishRepository parishRepo;

    public GetAllAdminVerificationDetailsService(AdminVerificationKeyRepository keyRepo, ParishRepository parishRepo) {
        this.keyRepo = keyRepo;
        this.parishRepo = parishRepo;
    }

    @Override
    public ResponseEntity<List<AdminVerificationDetailsDTO>> execute(AdminVerificationFilterDTO filterDto) {
        String statusFilter = filterDto.getStatusFilter() == null ? "ALL" : filterDto.getStatusFilter().trim().toUpperCase();
        String dateFilterType = filterDto.getDateFilterType() == null ? "ALL" : filterDto.getDateFilterType().trim().toUpperCase();

        LocalDate fromDateTemp = null;
        LocalDate toDateTemp = null;

        LocalDate today = LocalDate.now();

        switch (dateFilterType) {
            case "TODAY" -> {
                fromDateTemp = today;
                toDateTemp = today;
            }
            case "THIS_WEEK" -> {
                fromDateTemp = today.with(java.time.DayOfWeek.MONDAY);
                toDateTemp = today.with(java.time.DayOfWeek.SUNDAY);
            }
            case "THIS_MONTH" -> {
                fromDateTemp = today.withDayOfMonth(1);
                toDateTemp = today.withDayOfMonth(today.lengthOfMonth());
            }
            case "CUSTOM" -> {
                fromDateTemp = filterDto.getFromDate();
                toDateTemp = filterDto.getToDate();
            }
            case "ALL" -> {
                // No date filter applied
            }
        }

        // Make final copies for lambda use
        final LocalDate fromDate = fromDateTemp;
        final LocalDate toDate = toDateTemp;

        List<AdminVerificationDetailsDTO> dtos = keyRepo.findAll().stream()
                .map(this::toDTO)
                .filter(dto -> switch (statusFilter) {
                    case "VERIFIED"   -> dto.getVerificationStatus() == AdminVerificationStatus.VERIFIED;
                    case "UNVERIFIED" -> dto.getVerificationStatus() == AdminVerificationStatus.NOT_VERIFIED;
                    default           -> true;
                })
                .filter(dto -> {
                    if (fromDate != null && toDate != null) {
                        LocalDate keyDate = dto.getKeyCreatedAt().toLocalDate();
                        return !keyDate.isBefore(fromDate) && !keyDate.isAfter(toDate);
                    }
                    return true;  // No date filter
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }



    private AdminVerificationDetailsDTO toDTO(AdminVerificationKey key) {
        var user = key.getUser();

        Long parishId = user.getParishId();
        String parishName = null;
        String parishLocation = null;

        if (parishId != null) {
            var parish = parishRepo.findById(parishId).orElse(null);
            if (parish != null) {
                parishName = parish.getName();
                parishLocation = parish.getLocation();
            }
        }

        return new AdminVerificationDetailsDTO(
                key.getId(),                        // ✅ NEW: Key ID
                user.getId(),
                user.getEmail(),
                user.getName(),
                key.getKey(),
                key.isUsed(),
                user.getAdminVerificationStatus(),
                key.getCreatedAt(),
                parishId,
                parishName,
                parishLocation
        );
    }

}
