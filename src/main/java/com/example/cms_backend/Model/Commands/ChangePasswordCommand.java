package com.example.cms_backend.Model.Commands;


import com.example.cms_backend.Model.DTO.ChangePasswordDTO;

public class ChangePasswordCommand {
    private Long userId;
    private ChangePasswordDTO passwordDTO;

    public ChangePasswordCommand(Long userId, ChangePasswordDTO passwordDTO) {
        this.userId = userId;
        this.passwordDTO = passwordDTO;
    }

    public Long getUserId() {
        return userId;
    }

    public ChangePasswordDTO getPasswordDTO() {
        return passwordDTO;
    }
}

