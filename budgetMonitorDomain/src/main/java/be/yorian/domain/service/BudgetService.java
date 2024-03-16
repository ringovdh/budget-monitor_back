package be.yorian.domain.service;

import be.yorian.domain.dto.BudgetOverviewPerCategory;
import be.yorian.domain.dto.MonthlyBudgetOverview;
import be.yorian.domain.dto.YearlyBudgetOverview;

import java.util.List;

public interface BudgetService {
    MonthlyBudgetOverview getBudgetOverviewPerMonth(int month, int year);

    List<BudgetOverviewPerCategory> getBudgetOverviewPerCategory(Long categoryId, int year);

    YearlyBudgetOverview getBudgetOverviewPerYear(int year);

}
