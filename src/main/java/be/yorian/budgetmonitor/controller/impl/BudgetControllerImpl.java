package be.yorian.budgetmonitor.controller.impl;

import be.yorian.budgetmonitor.controller.BudgetController;
import be.yorian.budgetmonitor.dto.BudgetOverviewPerCategory;
import be.yorian.budgetmonitor.dto.MonthlyBudgetOverview;
import be.yorian.budgetmonitor.dto.YearlyBudgetOverview;
import be.yorian.budgetmonitor.service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class BudgetControllerImpl implements BudgetController {

    private final BudgetService budgetService;

    @Autowired
    public BudgetControllerImpl(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @Override
    @GetMapping(produces = "application/json", path="/budgets/period")
    public ResponseEntity<MonthlyBudgetOverview> getBudgetOverviewPerMonth(@RequestParam Optional<Integer>month,
                                                                           @RequestParam Optional<Integer>year) {
        return ResponseEntity.ok().body(budgetService.getBudgetOverviewPerMonth(month.orElse(1), year.orElse(2022)));
    }

    @Override
    @GetMapping(produces = "application/json", path="/budgets/category")
    public ResponseEntity<List<BudgetOverviewPerCategory>> getBudgetOverviewPerCategory(@RequestParam Optional<Long>categoryId,
                                                                                        @RequestParam Optional<Integer>year) {
        return ResponseEntity.ok().body(budgetService.getBudgetOverviewPerCategory(categoryId.orElse(0L), year.orElse(0)));
    }

    @Override
    @GetMapping(produces = "application/json", path="/budgets/year")
    public ResponseEntity<YearlyBudgetOverview> getBudgetOverviewPerYear(@RequestParam Optional<Integer>year) {
        return ResponseEntity.ok().body(budgetService.getBudgetOverviewPerYear(year.orElse(2022)));
    }
}
