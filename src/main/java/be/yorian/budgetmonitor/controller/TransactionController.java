package be.yorian.budgetmonitor.controller;

import be.yorian.budgetmonitor.dto.BudgetOverviewPerCategory;
import be.yorian.budgetmonitor.entity.Transaction;
import be.yorian.budgetmonitor.response.CustomResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface TransactionController {

	List<Transaction> getTransactions();
    ResponseEntity<CustomResponse> getTransactionsByComment(Optional<String> comment,
                                                            Optional<Integer> page,
                                                            Optional<Integer> size);
    ResponseEntity<List<BudgetOverviewPerCategory>> getTransactionsByCategory(Optional<Long> categoryId,
                                                                              Optional<Integer> year);
    List<Transaction> getTransactionsByMonth(String month, String year);
    Transaction saveTransaction(Transaction transaction);
    Transaction updateTransaction(Long id, Transaction transaction);
    void deleteTransaction(Long id);
	
}
