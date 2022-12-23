package be.yorian.budgetmonitor.service;

import be.yorian.budgetmonitor.entity.Transaction;

import java.util.List;

public interface TransactionService {

    List<Transaction> getTransactions();
    void saveTransaction(Transaction transaction);
    List<Transaction> getTransactionsByYear(String year);
    List<Transaction> getTransactionsByMonth(String month, String year);
}
