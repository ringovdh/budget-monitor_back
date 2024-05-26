package be.yorian.budgetbuddy.controller;

import be.yorian.budgetbuddy.adapter.database.entity.TransactionEntity;
import be.yorian.budgetbuddy.dto.BudgetOverviewPerCategory;
import be.yorian.budgetbuddy.dto.TransactionDTO;
import be.yorian.budgetbuddy.model.Transaction;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface TransactionController {

	List<TransactionEntity> getTransactions();

    ResponseEntity<List<BudgetOverviewPerCategory>> getTransactionsByCategory(Optional<Long> categoryId,
                                                                              Optional<Integer> year);
    List<TransactionEntity> getTransactionsByMonth(String month, String year);
    TransactionDTO createTransaction(Transaction transaction);
    TransactionEntity updateTransaction(Long id, TransactionEntity transaction);
    void deleteTransaction(Long id);
	
}
