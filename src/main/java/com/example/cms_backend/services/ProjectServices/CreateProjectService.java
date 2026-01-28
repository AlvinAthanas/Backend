package com.example.cms_backend.services.ProjectServices;

import com.example.cms_backend.abstractions.Command;
import com.example.cms_backend.exceptions.UserNotFoundException;
import com.example.cms_backend.model.Commands.CreateProjectCommand;
import com.example.cms_backend.model.DTO.CreateProjectDTO;
import com.example.cms_backend.model.Entities.Project;
import com.example.cms_backend.model.Entities.User;
import com.example.cms_backend.repositories.ProjectRepository;
import com.example.cms_backend.repositories.UserRepository;
import com.example.cms_backend.utils.LoggedInUserUtil;
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


            User user = userRepository.findByEmail(LoggedInUserUtil.loggedInUserEmail(request))
                    .orElseThrow(UserNotFoundException::new);

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


}

