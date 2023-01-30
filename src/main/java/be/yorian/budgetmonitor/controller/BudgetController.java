package be.yorian.budgetmonitor.controller;

import be.yorian.budgetmonitor.dto.BudgetOverviewPerCategory;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface BudgetController {

    ResponseEntity<List<BudgetOverviewPerCategory>> getBudgetOverviewPerCategory(Optional<Integer> month,
                                                                                 Optional<Integer> year);

}
