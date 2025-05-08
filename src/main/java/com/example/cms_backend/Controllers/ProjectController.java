package com.example.cms_backend.Controllers;

import com.example.cms_backend.Model.Commands.UpdateProjectCommand;
import com.example.cms_backend.Model.Entities.Project;
import com.example.cms_backend.Services.ProjectServices.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost") // Allow frontend requests
public class ProjectController {
    private final CreateProjectService createProjectService;
    private final GetProjectService getProjectService;
    private final GetProjectsService getProjectsService;
    private final UpdateProjectService updateProjectService;
    private final DeleteProjectService deleteProjectService;

    public ProjectController(CreateProjectService createProjectService,
                             GetProjectService getProjectService,
                             GetProjectsService getProjectsService,
                             UpdateProjectService updateProjectService,
                             DeleteProjectService deleteProjectService) {
        this.createProjectService = createProjectService;
        this.getProjectService = getProjectService;
        this.getProjectsService = getProjectsService;
        this.updateProjectService = updateProjectService;
        this.deleteProjectService = deleteProjectService;
    }

    @PostMapping("/project")
    ResponseEntity<Project> createProject(@RequestBody Project project) {
        return createProjectService.execute(project);
    }

    @GetMapping("/project/{id}")
    ResponseEntity<Project> getProject(@PathVariable Long id) {
        return getProjectService.execute(id);
    }

    @GetMapping("/projects")
    ResponseEntity<List<Project>> getProjects() {
        return getProjectsService.execute(null);
    }

    @PutMapping("/project/{id}")
    ResponseEntity<Project> updateProject(@PathVariable Long id, @RequestBody Project project) {
        return updateProjectService.execute(new UpdateProjectCommand(id, project));
    }

    @DeleteMapping("/project/{id}")
    ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        return deleteProjectService.execute(id);
    }
}
