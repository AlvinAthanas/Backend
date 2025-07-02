package com.example.cms_backend.Model.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AdminVerificationFilterDTO {
    private String statusFilter;        // "VERIFIED", "UNVERIFIED", "ALL"
    private String dateFilterType;      // "TODAY", "THIS_WEEK", "THIS_MONTH", "CUSTOM", null
    private LocalDate fromDate;         // Used for CUSTOM range or specific date
    private LocalDate toDate;           // Used for CUSTOM range
}
