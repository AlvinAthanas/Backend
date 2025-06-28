package com.example.cms_backend.Model.DTO;

import com.example.cms_backend.Model.Entities.User;
import com.example.cms_backend.Model.Enums.Gender;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

import java.util.Base64;
import java.util.Optional;

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
    private String profilePicture; // Base64 string
    private String password;

    public UserDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.address = user.getAddress();
        this.gender = user.getGender();
        this.parishId = user.getParishId();
        this.profilePicture = encodeImageToBase64(user.getProfilePicture());
        this.password = user.getPassword();
    }

    public UserDTO(Optional<User> user) {
        if (user.isEmpty()) return;
        this.id = user.get().getId();
        this.name = user.get().getName();
        this.email = user.get().getEmail();
        this.phone = user.get().getPhone();
        this.address = user.get().getAddress();
        this.gender = user.get().getGender();
        this.parishId = user.get().getParishId();
        this.profilePicture = encodeImageToBase64(user.get().getProfilePicture());
    }

    private String encodeImageToBase64(byte[] imageData) {
        if (imageData == null) return null;
        return Base64.getEncoder().encodeToString(imageData);
    }
}
