package com.example.cms_backend.Model.DTO;

import com.example.cms_backend.Model.Entities.JoinCommunityRequest;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
public class JoinCommunityRequestDTO {
    private Long id;
    private Long userId;
    private Long groupId;
    private String status;
    private LocalDateTime submittedAt;

    public static JoinCommunityRequestDTO from(JoinCommunityRequest entity) {
        JoinCommunityRequestDTO dto = new JoinCommunityRequestDTO();
        dto.setId(entity.getId());
        dto.setUserId(entity.getUserId());
        dto.setGroupId(entity.getGroupId());
        dto.setStatus(entity.getStatus().name());
        dto.setSubmittedAt(entity.getSubmittedAt());
        return dto;
    }
}

