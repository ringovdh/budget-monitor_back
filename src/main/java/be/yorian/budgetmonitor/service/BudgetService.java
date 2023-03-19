package be.yorian.budgetmonitor.service;

import be.yorian.budgetmonitor.dto.BudgetOverviewPerCategory;
import be.yorian.budgetmonitor.dto.BudgetOverviewPerMonth;
import be.yorian.budgetmonitor.dto.BudgetOverviewPerYear;

import java.util.List;

public interface BudgetService {
    List<BudgetOverviewPerMonth> getBudgetOverviewPerMonth(int month, int year);

    List<BudgetOverviewPerCategory> getBudgetOverviewPerCategory(Long categoryId, int year);

    List<BudgetOverviewPerYear> getBudgetOverviewPerYear(int year);

}
