package be.yorian.budgetmonitor.controller;

import be.yorian.budgetmonitor.dto.BudgetOverviewPerCategory;
import be.yorian.budgetmonitor.dto.MonthlyBudgetOverview;
import be.yorian.budgetmonitor.dto.YearlyBudgetOverview;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface BudgetController {

    ResponseEntity<MonthlyBudgetOverview> getBudgetOverviewPerMonth(Optional<Integer> month,
                                                                    Optional<Integer> year);

    ResponseEntity<List<BudgetOverviewPerCategory>> getBudgetOverviewPerCategory(Optional<Long> categoryId,
                                                                                 Optional<Integer> year);

    ResponseEntity<YearlyBudgetOverview> getBudgetOverviewPerYear(Optional<Integer> year);

}
