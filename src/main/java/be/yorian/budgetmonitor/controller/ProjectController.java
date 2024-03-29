package be.yorian.budgetmonitor.controller;

import be.yorian.budgetmonitor.dto.ProjectOverview;
import be.yorian.budgetmonitor.entity.Project;
import be.yorian.budgetmonitor.response.CustomResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface ProjectController {

    ResponseEntity<CustomResponse> getProjectByProjectname(Optional<String> projectname,
                                                           Optional<Integer> page,
                                                           Optional<Integer> size);

    List<Project> getProjects();

    List<ProjectOverview> getProjectOverview();

    void createProject(Project project);

    void updateProject(Long project_id,
                       Project project);

    void deleteProject(Long project_id);
}
