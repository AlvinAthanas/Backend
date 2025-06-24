package com.example.cms_backend.Model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class GroupContributionSubmissionDTO {
    private Long id;
    private Long requirementId;
    private Long userId;
    private Long amount;
    private LocalDate date;
    private String note;
    private String submittedByName;   // optional for display
    private String type;

    public GroupContributionSubmissionDTO(Long id,
                                          Long requirementId,
                                          Long userId,
                                          Long amount,
                                          LocalDate date,
                                          String note) {
        this.id = id;
        this.requirementId = requirementId;
        this.userId = userId;
        this.amount = amount;
        this.date = date;
        this.note = note;
    }
}
