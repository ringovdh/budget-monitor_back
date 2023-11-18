package be.yorian.budgetmonitor.service.impl;

import be.yorian.budgetmonitor.dto.ProjectOverview;
import be.yorian.budgetmonitor.entity.Project;
import be.yorian.budgetmonitor.entity.Transaction;
import be.yorian.budgetmonitor.repository.ProjectRepository;
import be.yorian.budgetmonitor.repository.TransactionRepository;
import be.yorian.budgetmonitor.service.ProjectService;
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
    private final TransactionRepository transactionRepository;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository,
                              TransactionRepository transactionRepository) {
        this.projectRepository = projectRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Page<Project> getProjectsByProjectname(String projectname, int page, int size) {
        return projectRepository.findByProjectnameContaining(projectname, of(page, size));
    }

    @Override
    public List<ProjectOverview> getProjects() {
        return projectRepository.findAll().stream().map(
                this::mapProjectToProjectOverviewDTO
        ).toList();
    }

    private ProjectOverview mapProjectToProjectOverviewDTO(Project p) {
        List<Transaction> transactions = transactionRepository.findByProjectProjectname(p.getProjectname());
        return new ProjectOverview(
                p.getId(),
                p.getProjectname(),
                p.getDescription(),
                p.getStartdate(),
                p.getEnddate(),
                p.getIcon(),
                transactions,
                calculateProjectTotal(transactions)
        );
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

    public BigDecimal calculateProjectTotal(List<Transaction> transactions) {
        return new BigDecimal(
                transactions.stream()
                        .mapToDouble(Transaction::getAmount)
                        .sum());
    }
}
