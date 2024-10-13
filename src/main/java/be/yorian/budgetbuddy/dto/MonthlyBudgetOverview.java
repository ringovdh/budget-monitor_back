package be.yorian.budgetbuddy.dto;

import java.util.List;

public record MonthlyBudgetOverview(
        List<BudgetPerCategory> budgetsPerCategory,
        GraphData graphData,
        List<ProjectData> projectsData) {
}
