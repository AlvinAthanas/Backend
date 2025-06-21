package com.example.cms_backend.Model.Commands;

import com.example.cms_backend.Model.Enums.SacramentType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import jakarta.servlet.http.HttpServletRequest;

@Getter
@Setter
public class CreateSacramentRegistrationCommand {
    private Long userId;
    private SacramentType sacramentType;
    private LocalDate registrationDate;
    private LocalDate startDate;
    private LocalDate completionDate;
    private HttpServletRequest request;  // NEW FIELD
}

