package be.yorian.domain.controller;

import be.yorian.domain.dto.ProjectOverview;
import be.yorian.domain.entity.Project;
import be.yorian.domain.response.CustomResponse;
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
