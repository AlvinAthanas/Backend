package com.example.cms_backend.Model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class SacramentSessionInfo {
    private LocalDate startDate;
    private LocalDate completionDate;
}
