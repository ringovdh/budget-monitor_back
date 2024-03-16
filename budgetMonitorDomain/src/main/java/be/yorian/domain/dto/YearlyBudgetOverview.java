package be.yorian.domain.dto;

import java.util.List;

public record YearlyBudgetOverview(
        List<BudgetPerMonth> budgetsPerMonth,
        GraphData graphData,
        SavingsData savingsData,
        List<ProjectData> projectsData){ }
