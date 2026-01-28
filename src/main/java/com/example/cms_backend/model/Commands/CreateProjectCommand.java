package com.example.cms_backend.model.Commands;

import com.example.cms_backend.model.DTO.CreateProjectDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

public class CreateProjectCommand {
    private final CreateProjectDTO projectDTO;
    private final MultipartFile featuredImage;
    private final HttpServletRequest request;

    public CreateProjectCommand(CreateProjectDTO projectDTO, MultipartFile featuredImage, HttpServletRequest request) {
        this.projectDTO = projectDTO;
        this.featuredImage = featuredImage;
        this.request = request;
    }

    public CreateProjectDTO getProjectDTO() {
        return projectDTO;
    }

    public MultipartFile getFeaturedImage() {
        return featuredImage;
    }

    public HttpServletRequest getRequest() {
        return request;
    }
}
