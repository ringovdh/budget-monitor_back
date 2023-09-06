package be.yorian.budgetmonitor.service;

import be.yorian.budgetmonitor.entity.Transaction;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TransactionService {

    List<Transaction> getTransactions();
    Transaction saveTransaction(Transaction transaction);
    List<Transaction> getTransactionsByMonth(String month, String year);
    Page<Transaction> getTransactionsByComment(String comment, Integer page, Integer size);
    Transaction updateTransaction(Long id, Transaction transaction);
    void deleteTransaction(Long id);
}
