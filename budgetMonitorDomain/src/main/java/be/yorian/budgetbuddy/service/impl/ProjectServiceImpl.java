package be.yorian.budgetbuddy.service.impl;

import be.yorian.budgetbuddy.adapter.database.entity.TransactionEntity;
import be.yorian.budgetbuddy.adapter.database.repository.TransactionEntityRepository;
import be.yorian.budgetbuddy.dto.ProjectOverview;
import be.yorian.budgetbuddy.model.Project;
import be.yorian.budgetbuddy.model.Transaction;
import be.yorian.budgetbuddy.repository.ProjectRepository;
import be.yorian.budgetbuddy.service.ProjectService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.data.domain.PageRequest.of;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final TransactionEntityRepository transactionRepository;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository,
                              TransactionEntityRepository transactionRepository) {
        this.projectRepository = projectRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Page<Project> getProjectByProjectname(String projectname, int page, int size) {
        return projectRepository.findByProjectnameContaining(projectname, of(page, size));
    }

    @Override
    public List<Project> getProjects() {
        return projectRepository.findAll();
    }

    @Override
    public List<ProjectOverview> getProjectOverview() {
        return projectRepository.findAll().stream().map(
                this::mapProjectToProjectOverviewDTO
        ).toList();
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
        project.setDescription(updatedProject.getDescription());
        project.setStartdate(updatedProject.getStartdate());
        project.setEnddate(updatedProject.getEnddate());
        project.setIcon(updatedProject.getIcon());

        projectRepository.save(project);
    }

    @Override
    public void deleteProject(Long projectId) {
        projectRepository.deleteById(projectId);
    }

    private ProjectOverview mapProjectToProjectOverviewDTO(Project p) {
        List<TransactionEntity> transactionEntities = transactionRepository.findByProjectProjectname(p.getProjectname());
        return new ProjectOverview(
                p,
                transactionEntities,
                calculateProjectTotal(transactionEntities)
        );
    }

    private BigDecimal calculateProjectTotal(List<TransactionEntity> transactionEntities) {
        return BigDecimal.valueOf(transactionEntities.stream()
                .mapToDouble(TransactionEntity::getAmount)
                .sum());
    }
}