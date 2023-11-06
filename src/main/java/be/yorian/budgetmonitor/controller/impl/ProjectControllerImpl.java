package be.yorian.budgetmonitor.controller.impl;

import be.yorian.budgetmonitor.controller.ProjectController;
import be.yorian.budgetmonitor.dto.ProjectOverview;
import be.yorian.budgetmonitor.entity.Project;
import be.yorian.budgetmonitor.response.CustomResponse;
import be.yorian.budgetmonitor.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ProjectControllerImpl implements ProjectController {

    private final ProjectService projectService;

    @Autowired
    public ProjectControllerImpl(ProjectService projectService) {
        this.projectService = projectService;
    }

    @Override
    @GetMapping("/projects/projectname")
    public ResponseEntity<CustomResponse> getProjectsByProjectname(@RequestParam Optional<String> projectname,
                                                                   @RequestParam Optional<Integer> page,
                                                                   @RequestParam Optional<Integer> size) {
        Page<Project> projects = projectService.getProjectsByProjectname(projectname.orElse(""),
                page.orElse(0), size.orElse(10));
        CustomResponse response = new CustomResponse();
        response.setStatus(HttpStatus.OK);
        response.setStatusCode(HttpStatus.OK.value());
        Map<String, Page> dataMap = new HashMap<>();
        dataMap.put("page", projects);
        response.setData(dataMap);
        return ResponseEntity.ok().body(response);
    }

    @Override
    @GetMapping("/projects/")
    public List<ProjectOverview> getProjects() {
        return projectService.getProjects();
    }
    
    @Override
    @PostMapping("/projects/")
    public void createProject(@RequestBody Project project) {
        projectService.saveProject(project);
    }

    @Override
    @PutMapping("/projects/{project_id}")
    public void updateProject(@PathVariable("project_id")Long projectId,
                              @RequestBody Project project) {
        projectService.updateProject(projectId, project);
    }

    @Override
    @DeleteMapping("/projects/{project_id}")
    public void deleteProject(@PathVariable("project_id")Long projectId) {
        projectService.deleteProject(projectId);
    }

}
