package com.example.cms_backend.Model.DTO;

import com.example.cms_backend.Model.Enums.Gender;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserDTO {
    private String name;
    private String phone;
    private String address;
    private Gender gender;
    private Long parishId;
    // Leave out email if you want to prevent it from changing
}

