package com.example.cms_backend.model.Commands;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateGroupContributionRequirementCommand {
    private Long groupId;
    private String contributionType;
    private Long targetAmount;
    private LocalDate deadline;
    private String description;
    private Long declaredByUserId; // optionally injected from token
    private HttpServletRequest request;  // Injected by controller

}