package com.example.cms_backend.model.Commands;

import org.springframework.web.multipart.MultipartFile;

public class UploadParishImageCommand {
    private Long parishId;
    private MultipartFile file;

    public UploadParishImageCommand(Long parishId, MultipartFile file) {
        this.parishId = parishId;
        this.file = file;
    }

    public Long getParishId() {
        return parishId;
    }

    public MultipartFile getFile() {
        return file;
    }
}
