package be.yorian.budgetbuddy.controller;

import be.yorian.budgetbuddy.dto.BudgetOverviewPerCategory;
import be.yorian.budgetbuddy.dto.TransactionDTO;
import be.yorian.budgetbuddy.model.Transaction;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface TransactionController {

    ResponseEntity<List<BudgetOverviewPerCategory>> getTransactionsByCategory(Optional<Long> categoryId,
                                                                              Optional<Integer> year);
    TransactionDTO createTransaction(Transaction transaction);

    TransactionDTO updateTransaction(long id, Transaction transaction);

    void deleteTransaction(long id);
}
