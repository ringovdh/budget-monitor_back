package be.yorian.budgetmonitor.service;

import be.yorian.budgetmonitor.dto.BudgetOverviewPerCategory;

import java.util.List;

public interface BudgetService {
    List<BudgetOverviewPerCategory> getBudgetOverviewPerCategory(int month, int year);
}
