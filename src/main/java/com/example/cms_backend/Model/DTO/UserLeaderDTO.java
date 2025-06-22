package com.example.cms_backend.Model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserLeaderDTO {
    private Long userId;
    private String name;
    private String role;
}
