package com.example.cms_backend.Model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class GroupContributionSubmissionViewDTO {
    private Long id;
    private Long requirementId;
    private Long userId;
    private String userName; // for display
    private Long amount;
    private LocalDate date;
    private String note;
}
