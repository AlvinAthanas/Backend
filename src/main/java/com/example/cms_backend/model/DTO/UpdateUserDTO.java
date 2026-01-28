package com.example.cms_backend.model.DTO;

import com.example.cms_backend.model.Enums.Gender;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserDTO {
    private String name;
    private String email;
    private String phone;
    private String address;
    private Gender gender;
    private Long parishId;
    // Leave out email if you want to prevent it from changing
}

