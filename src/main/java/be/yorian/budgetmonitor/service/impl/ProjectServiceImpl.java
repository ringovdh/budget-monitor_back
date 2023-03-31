package be.yorian.budgetmonitor.service.impl;

import be.yorian.budgetmonitor.entity.Project;
import be.yorian.budgetmonitor.repository.ProjectRepository;
import be.yorian.budgetmonitor.service.ProjectService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import static org.springframework.data.domain.PageRequest.of;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public Page<Project> getProjectsByProjectname(String projectname, int page, int size) {
        return projectRepository.findByProjectnameContaining(projectname, of(page, size));
    }

    @Override
    public void saveProject(Project project) {
        projectRepository.save(project);
    }

    @Override
    public void updateProject(Long projectId, Project updatedProject) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("project_not_found"));
        project.setProjectname(updatedProject.getProjectname());

        projectRepository.save(project);
    }

    @Override
    public void deleteProject(Long projectId) {
        projectRepository.deleteById(projectId);
    }

}
