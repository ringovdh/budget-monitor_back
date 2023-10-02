package be.yorian.budgetmonitor.service;

import be.yorian.budgetmonitor.dto.BudgetOverviewPerCategory;
import be.yorian.budgetmonitor.dto.BudgetOverviewPerYear;
import be.yorian.budgetmonitor.dto.MonthlyBudgetOverview;

import java.util.List;

public interface BudgetService {
    MonthlyBudgetOverview getBudgetOverviewPerMonth(int month, int year);

    List<BudgetOverviewPerCategory> getBudgetOverviewPerCategory(Long categoryId, int year);

    List<BudgetOverviewPerYear> getBudgetOverviewPerYear(int year);

}
