package com.example.cms_backend.Model.DTO;

import com.example.cms_backend.Model.Enums.AdminVerificationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class AdminVerificationDetailsDTO {

    private Long keyId;                  // ✅ NEW FIELD: Verification Key ID
    private Long userId;
    private String email;
    private String fullName;
    private String verificationKey;
    private boolean keyUsed;
    private AdminVerificationStatus verificationStatus;
    private LocalDateTime keyCreatedAt;

    private Long parishId;
    private String parishName;
    private String parishLocation;
}
