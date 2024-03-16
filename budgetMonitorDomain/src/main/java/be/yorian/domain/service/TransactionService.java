package be.yorian.domain.service;

import be.yorian.domain.entity.Transaction;

import java.util.List;

public interface TransactionService {

    List<Transaction> getTransactions();
    Transaction saveTransaction(Transaction transaction);
    List<Transaction> getTransactionsByMonth(String month, String year);
    Transaction updateTransaction(Long id, Transaction transaction);
    void deleteTransaction(Long id);
}
