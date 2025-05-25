package com.example.cms_backend.Model.Commands;

import com.example.cms_backend.Model.DTO.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private UserDTO user;


}
