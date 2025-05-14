package com.example.cms_backend.Model.Commands;

import org.springframework.web.multipart.MultipartFile;

public class UploadProfilePictureCommand {
    private Long userId;
    private MultipartFile file;

    public UploadProfilePictureCommand(Long userId, MultipartFile file) {
        this.userId = userId;
        this.file = file;
    }

    public Long getUserId() {
        return userId;
    }

    public MultipartFile getFile() {
        return file;
    }
}
