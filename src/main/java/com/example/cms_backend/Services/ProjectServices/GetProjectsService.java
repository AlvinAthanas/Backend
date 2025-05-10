package com.example.cms_backend.Services.ProjectServices;

import com.example.cms_backend.Abstractions.Query;
import com.example.cms_backend.Model.Commands.GetProjectsCommand;
import com.example.cms_backend.Model.Entities.Project;
import com.example.cms_backend.Model.Entities.User;
import com.example.cms_backend.Repositories.ProjectRepository;
import com.example.cms_backend.Repositories.UserRepository;
import com.example.cms_backend.Services.JwtServices.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetProjectsService implements Query<GetProjectsCommand, List<Project>> {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public GetProjectsService(ProjectRepository projectRepository, UserRepository userRepository, JwtService jwtService) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @Override
    public ResponseEntity<List<Project>> execute(GetProjectsCommand command) {
        try {
            String email = jwtService.extractEmailFromRequest(command.getRequest());

            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            Long parishId = user.getParishId();
            List<Project> projects = projectRepository.findByParishId(parishId);

            return ResponseEntity.ok(projects);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
