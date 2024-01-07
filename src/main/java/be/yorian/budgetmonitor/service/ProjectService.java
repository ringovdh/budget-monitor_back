package be.yorian.budgetmonitor.service;

import be.yorian.budgetmonitor.dto.ProjectOverview;
import be.yorian.budgetmonitor.entity.Project;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProjectService {

    void saveProject(Project project);

    Page<Project> getProjectByProjectname(String projectname, int page, int size);

    void updateProject(Long projectId, Project project);

    void deleteProject(Long projectId);

    List<Project> getProjects();

    List<ProjectOverview> getProjectOverview();
}
