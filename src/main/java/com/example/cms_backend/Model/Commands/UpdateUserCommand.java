package com.example.cms_backend.Model.Commands;

import com.example.cms_backend.Model.DTO.UpdateUserDTO;
import com.example.cms_backend.Model.Entities.User;

public class UpdateUserCommand {
    private Long id;
    private UpdateUserDTO updateUserDTO;

    public UpdateUserCommand(Long id, UpdateUserDTO dto) {
        this.id = id;
        this.updateUserDTO = dto;
    }

    public Long getId() {
        return id;
    }

    public UpdateUserDTO getUpdateUserDTO() {
        return updateUserDTO;
    }
}

