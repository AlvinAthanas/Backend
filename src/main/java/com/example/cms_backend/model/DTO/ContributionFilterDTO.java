package com.example.cms_backend.model.DTO;

import lombok.Data;

@Data
public class ContributionFilterDTO {
    private String type;
    private Integer month;
    private Integer year;

    // Constructors
    public ContributionFilterDTO() {}
    public ContributionFilterDTO(String type, Integer month, Integer year) {
        this.type = type;
        this.month = month;
        this.year = year;
    }

}
