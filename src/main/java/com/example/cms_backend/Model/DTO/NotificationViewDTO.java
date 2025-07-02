package com.example.cms_backend.Model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class NotificationViewDTO {
    private Long id;
    private String title;
    private String message;
    private LocalDate date;
    private Boolean isGlobal;
    private Long userId;
    private Long senderId;
    private Long groupId;
    private String targetGroupName;
    private Long kandaId;
    private String targetKandaName;
    private Long parishId;
}
