package be.yorian.budgetmonitor.controller;

import be.yorian.budgetmonitor.dto.ProjectOverviewDTO;
import be.yorian.budgetmonitor.entity.Project;
import be.yorian.budgetmonitor.response.CustomResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface ProjectController {

    ResponseEntity<CustomResponse> getProjectsByProjectname(Optional<String> projectname,
                                                            Optional<Integer> page,
                                                            Optional<Integer> size);

    List<ProjectOverviewDTO> getProjects();

    void createProject(Project project);

    void updateProject(Long project_id,
                       Project project);

    void deleteProject(Long project_id);
}
