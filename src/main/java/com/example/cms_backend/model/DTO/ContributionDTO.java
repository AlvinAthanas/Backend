package com.example.cms_backend.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ContributionDTO {
    private Long id;
    private Long amount;
    private String type;
    private String description;
    private LocalDate date;
}
