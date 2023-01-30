package be.yorian.budgetmonitor.controller;

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
    List<Transaction> getTransactionsByMonth(String month, String year);
    Transaction saveTransaction(Transaction transaction);
    void updateTransaction(Long id, Transaction transaction);
    void deleteTransaction(Long id);
	
}
