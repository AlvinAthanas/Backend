package com.example.cms_backend.Services.AdminVerificationKeyServices;

import com.example.cms_backend.Abstractions.Query;
import com.example.cms_backend.Model.DTO.AdminVerificationDetailsDTO;
import com.example.cms_backend.Model.Entities.AdminVerificationKey;
import com.example.cms_backend.Model.Enums.AdminVerificationStatus;
import com.example.cms_backend.Repositories.AdminVerificationKeyRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetAllAdminVerificationDetailsService
        implements Query<String, List<AdminVerificationDetailsDTO>> {

    private final AdminVerificationKeyRepository keyRepo;

    public GetAllAdminVerificationDetailsService(AdminVerificationKeyRepository keyRepo) {
        this.keyRepo = keyRepo;
    }

    @Override
    public ResponseEntity<List<AdminVerificationDetailsDTO>> execute(String statusFilter) {

        // normalise filter
        String filter = statusFilter == null ? "ALL" : statusFilter.trim().toUpperCase();

        List<AdminVerificationDetailsDTO> dtos = keyRepo.findAll()        // single DB hit
                .stream()
                // map first (cheap) …
                .map(this::toDTO)
                // … then filter by requested status
                .filter(dto -> switch (filter) {
                    case "VERIFIED"   -> dto.getVerificationStatus() == AdminVerificationStatus.VERIFIED;
                    case "UNVERIFIED" -> dto.getVerificationStatus() == AdminVerificationStatus.NOT_VERIFIED;
                    default           -> true;   // "ALL"
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    private AdminVerificationDetailsDTO toDTO(AdminVerificationKey key) {
        var user = key.getUser();
        return new AdminVerificationDetailsDTO(
                user.getId(),
                user.getEmail(),
                user.getName(),
                key.getKey(),     // column renamed from "key" → "verification_key"
                key.isUsed(),
                user.getAdminVerificationStatus(),
                key.getCreatedAt()
        );
    }
}
