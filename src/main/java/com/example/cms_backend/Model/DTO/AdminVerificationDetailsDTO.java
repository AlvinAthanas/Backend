package com.example.cms_backend.Model.DTO;

import com.example.cms_backend.Model.Enums.AdminVerificationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class AdminVerificationDetailsDTO {

    private Long userId;
    private String email;
    private String fullName;
    private String verificationKey;
    private boolean keyUsed;
    private AdminVerificationStatus verificationStatus;   // VERIFIED / NOT_VERIFIED
    private LocalDateTime keyCreatedAt;
}
