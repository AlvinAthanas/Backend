package com.example.cms_backend.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class GroupContributionDebtDTO {
    private Long requirementId;
    private String contributionType;
    private String groupName;
    private Long targetAmount;
    private Long totalCollected;
    private Long groupRemaining;
    private Long groupSize;
    private Long personalShare;
    private Long userContributed;
    private Long userRemaining;
    private LocalDate deadline;
}
