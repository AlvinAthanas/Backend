package com.example.cms_backend.Model.Commands;

import com.example.cms_backend.Model.Enums.SacramentType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateSacramentCompletionStatusCommand {
    private Long candidateId;
    private SacramentType sacramentType;
    private boolean completed;
}
