package com.example.cms_backend.model.Commands;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SubmitGroupContributionCommand {
    private Long requirementId;
    private Long userId;
    private Long amount;
    private LocalDate date;
    private String note;
}
