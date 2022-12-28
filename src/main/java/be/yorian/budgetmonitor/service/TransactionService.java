package be.yorian.budgetmonitor.service;

import be.yorian.budgetmonitor.entity.Transaction;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TransactionService {

    List<Transaction> getTransactions();
    void saveTransaction(Transaction transaction);
    List<Transaction> getTransactionsByYear(String year);
    List<Transaction> getTransactionsByMonth(String month, String year);
    Page<Transaction> getTransactionsByComment(String comment, Integer page, Integer size);
    void updateTransaction(Long id, Transaction transaction);
    void deleteTransaction(Long id);
}
