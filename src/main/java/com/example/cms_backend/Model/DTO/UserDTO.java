package com.example.cms_backend.Model.DTO;

import com.example.cms_backend.Model.Entities.User;
import com.example.cms_backend.Model.Enums.Gender;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String address;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private Long parishId;

    public UserDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.address = user.getAddress();
        this.gender = user.getGender();
        this.parishId = user.getParishId();
    }

}
