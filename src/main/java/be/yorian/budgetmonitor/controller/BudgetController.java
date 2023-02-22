package be.yorian.budgetmonitor.controller;

import be.yorian.budgetmonitor.dto.BudgetOverviewPerCategory;
import be.yorian.budgetmonitor.dto.BudgetOverviewPerMonth;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface BudgetController {

    ResponseEntity<List<BudgetOverviewPerMonth>> getBudgetOverviewPerMonth(Optional<Integer> month,
                                                                             Optional<Integer> year);
    ResponseEntity<List<BudgetOverviewPerCategory>> getBudgetOverviewPerCategory(Optional<Long> categoryId,
                                                                                 Optional<Integer> year);
}
