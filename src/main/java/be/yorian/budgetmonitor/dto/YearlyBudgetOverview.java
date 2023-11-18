package be.yorian.budgetmonitor.dto;

import java.util.List;

public record YearlyBudgetOverview(
        List<BudgetPerMonth> budgetsPerMonth,
        GraphData graphData,
        List<ProjectData> projectsData){ }
