package be.yorian.domain.controller.impl;

import be.yorian.domain.controller.TransactionController;
import be.yorian.domain.dto.BudgetOverviewPerCategory;
import be.yorian.domain.entity.Transaction;
import be.yorian.domain.response.CustomResponse;
import be.yorian.domain.service.BudgetService;
import be.yorian.domain.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class TransactionControllerImpl implements TransactionController {

    private final TransactionService transactionService;
    private final BudgetService budgetService;

    @Autowired
    public TransactionControllerImpl(TransactionService transactionService,
                                     BudgetService budgetService) {
        this.transactionService = transactionService;
        this.budgetService = budgetService;
    }

    @Override
    @GetMapping("/transactions")
    public List<Transaction> getTransactions() {
        return transactionService.getTransactions();
    }

    @Override
    @GetMapping(produces = "application/json", path="transactions/category")
    public ResponseEntity<List<BudgetOverviewPerCategory>> getTransactionsByCategory(@RequestParam Optional<Long> categoryId,
                                                                    @RequestParam Optional<Integer> year) {
        List<BudgetOverviewPerCategory> budgetOverviewPerCategory = budgetService.getBudgetOverviewPerCategory(categoryId.orElse(0L),
                year.orElse(0));
        CustomResponse response = new CustomResponse();
        response.setStatus(HttpStatus.OK);
        response.setStatusCode(HttpStatus.OK.value());
        Map<String, List<BudgetOverviewPerCategory>> dataMap = new HashMap<>();
        dataMap.put("page", budgetOverviewPerCategory);
        response.setData(dataMap);

        return ResponseEntity.ok().body(budgetOverviewPerCategory);
    }

    @Override
    @GetMapping("/transactions/{month}/{year}")
    public List<Transaction> getTransactionsByMonth(@PathVariable String month, @PathVariable String year) {
        return transactionService.getTransactionsByMonth(month, year);
    }

	@Override
    @PostMapping("/transactions")
    public Transaction saveTransaction(@RequestBody Transaction transaction) {
        return transactionService.saveTransaction(transaction);
    }

    @Override
    @PutMapping("/transactions/{id}")
    public Transaction updateTransaction(@PathVariable("id")Long id, @RequestBody Transaction transaction) {
        return transactionService.updateTransaction(id, transaction);
    }

    @Override
    @DeleteMapping("/transactions/{id}")
    public void deleteTransaction(@PathVariable("id")Long id) {
        transactionService.deleteTransaction(id);
    }

}