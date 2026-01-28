package com.example.cms_backend.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GroupContributionMemberProgressDTO {
    private Long userId;
    private String userName;
    private Long totalAmountContributed;
}
