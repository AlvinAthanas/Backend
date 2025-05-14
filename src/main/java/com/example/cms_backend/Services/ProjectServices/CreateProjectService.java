package com.example.cms_backend.Services.ProjectServices;

import com.example.cms_backend.Abstractions.Command;
import com.example.cms_backend.Model.Commands.CreateProjectCommand;
import com.example.cms_backend.Model.DTO.CreateProjectDTO;
import com.example.cms_backend.Model.Entities.Project;
import com.example.cms_backend.Model.Entities.User;
import com.example.cms_backend.Repositories.ProjectRepository;
import com.example.cms_backend.Repositories.UserRepository;
import com.example.cms_backend.Security.Jwt.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CreateProjectService implements Command<CreateProjectCommand, Project> {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public CreateProjectService(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<Project> execute(CreateProjectCommand command) {
        try {
            CreateProjectDTO dto = command.getProjectDTO();
            MultipartFile featuredImage = command.getFeaturedImage();
            HttpServletRequest request = command.getRequest();

            String token = extractTokenFromRequest(request);
            String email = JwtUtil.extractUsername(token);

            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            Project project = new Project();
            project.setName(dto.getName());
            project.setDescription(dto.getDescription());
            project.setBudget(dto.getBudget());
            project.setCollected(dto.getCollected());
            project.setParishId(user.getParishId());

            if (featuredImage != null && !featuredImage.isEmpty()) {
                project.setFeaturedImage(featuredImage.getBytes());
            }

            projectRepository.save(project);
            return ResponseEntity.status(HttpStatus.CREATED).body(project);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    private String extractTokenFromRequest(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        throw new RuntimeException("Invalid Authorization header");
    }
}

