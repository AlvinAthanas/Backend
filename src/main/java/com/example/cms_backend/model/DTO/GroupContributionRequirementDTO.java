package com.example.cms_backend.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupContributionRequirementDTO {
    private Long id;
    private Long groupId;
    private String groupName;
    private String contributionType;
    private Long targetAmount;
    private LocalDate deadline;
    private String description;
    private Long declaredByUserId;
    private Long totalContributedAmount;
    private boolean fulfilled;


    public GroupContributionRequirementDTO(Long id,
                                           Long groupId,
                                           String contributionType,
                                           Long targetAmount,
                                           LocalDate deadline,
                                           String description,
                                           Long declaredByUserId) {
        this.id = id;
        this.groupId = groupId;
        this.contributionType = contributionType;
        this.targetAmount = targetAmount;
        this.deadline = deadline;
        this.description = description;
        this.declaredByUserId = declaredByUserId;
    }

    public GroupContributionRequirementDTO(Long id,
                                           Long groupId,
                                           String groupName,
                                           String contributionType,
                                           Long targetAmount,
                                           LocalDate deadline,
                                           String description,
                                           Long declaredByUserId) {
        this.id = id;
        this.groupId = groupId;
        this.groupName = groupName;
        this.contributionType = contributionType;
        this.targetAmount = targetAmount;
        this.deadline = deadline;
        this.description = description;
        this.declaredByUserId = declaredByUserId;
    }
}