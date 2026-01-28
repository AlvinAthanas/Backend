package com.example.cms_backend.model.Commands;

import com.example.cms_backend.model.Enums.SacramentType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UpdateSacramentRegistrationCommand {
    private Long id;
    private SacramentType sacramentType;
    private boolean isCompleted;
    private LocalDate completionDate;
}
