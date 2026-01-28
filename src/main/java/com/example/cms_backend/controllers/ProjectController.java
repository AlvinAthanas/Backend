package com.example.cms_backend.controllers;

import com.example.cms_backend.model.Commands.CreateProjectCommand;
import com.example.cms_backend.model.Commands.GetProjectsCommand;
import com.example.cms_backend.model.Commands.UpdateProjectCommand;
import com.example.cms_backend.model.DTO.CreateProjectDTO;
import com.example.cms_backend.model.DTO.UpdateProjectDTO;
import com.example.cms_backend.model.Entities.Project;
import com.example.cms_backend.repositories.UserRepository;
import com.example.cms_backend.services.JwtServices.JwtService;
import com.example.cms_backend.services.ProjectServices.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost") // Allow frontend requests
public class ProjectController {
    private final CreateProjectService createProjectService;
    private final GetProjectService getProjectService;
    private final GetProjectsService getProjectsService;
    private final UpdateProjectService updateProjectService;
    private final DeleteProjectService deleteProjectService;
    private final CountProjectsPerParishService countProjectsPerParishService;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public ProjectController(CreateProjectService createProjectService,
                             GetProjectService getProjectService,
                             GetProjectsService getProjectsService,
                             UpdateProjectService updateProjectService,
                             DeleteProjectService deleteProjectService,
                             CountProjectsPerParishService countProjectsPerParishService,
                             JwtService jwtService,
                             UserRepository userRepository) {
        this.createProjectService = createProjectService;
        this.getProjectService = getProjectService;
        this.getProjectsService = getProjectsService;
        this.updateProjectService = updateProjectService;
        this.deleteProjectService = deleteProjectService;
        this.countProjectsPerParishService = countProjectsPerParishService;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }
    @PostMapping(value = "/project", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Project> addProject(
            @RequestPart("project") CreateProjectDTO projectDTO,
            @RequestPart(value = "featuredImage", required = false) MultipartFile featuredImage,
            HttpServletRequest request) {

        return createProjectService.execute(new CreateProjectCommand(projectDTO, featuredImage, request));
    }






    @GetMapping("/project/{id}")
    ResponseEntity<Project> getProject(@PathVariable Long id) {
        return getProjectService.execute(id);
    }

    @GetMapping("/projects")
    public ResponseEntity<List<Project>> getProjects(HttpServletRequest request) {
        return getProjectsService.execute(new GetProjectsCommand(request));
    }




    @PutMapping(value = "/project/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Project> updateProject(
            @PathVariable Long id,
            @RequestPart("project") UpdateProjectDTO projectDTO,
            @RequestPart(value = "featuredImage", required = false) MultipartFile featuredImage
    ) throws IOException {
        Project project = new Project();
        project.setId(id);
        project.setName(projectDTO.getName());
        project.setDescription(projectDTO.getDescription());
        project.setBudget(projectDTO.getBudget());
        project.setCollected(projectDTO.getCollected());

        if (featuredImage != null && !featuredImage.isEmpty()) {
            project.setFeaturedImage(featuredImage.getBytes());
        }

        return updateProjectService.execute(new UpdateProjectCommand(id, project));
    }



    @DeleteMapping("/project/{id}")
    ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        return deleteProjectService.execute(id);
    }

    @GetMapping("/parish/{parishId}/project-count")
    public ResponseEntity<Long> getProjectCount(@PathVariable Long parishId) {
        return countProjectsPerParishService.execute(parishId);
    }

}
