package com.example.cms_backend.model.Commands;

import com.example.cms_backend.model.Enums.SacramentType;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class GetSessionFilterCommand {
    private final HttpServletRequest request;
    private final SacramentType sacramentType;
    private final LocalDate startDate;
    private final LocalDate completionDate;



}
