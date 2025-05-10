package com.example.cms_backend.Model.Commands;

import com.example.cms_backend.Model.Entities.Project;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
@Getter
public class CreateProjectCommand {
    private final Project project;
    private final MultipartFile featuredImage;
    private final HttpServletRequest request;

    public CreateProjectCommand(Project project, MultipartFile featuredImage, HttpServletRequest request) {
        this.project = project;
        this.featuredImage = featuredImage;
        this.request = request;
    }

}
