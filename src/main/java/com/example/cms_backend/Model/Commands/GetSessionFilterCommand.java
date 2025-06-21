package com.example.cms_backend.Model.Commands;

import com.example.cms_backend.Model.Enums.SacramentType;
import com.example.cms_backend.Utils.LoggedInUserUtil;
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
