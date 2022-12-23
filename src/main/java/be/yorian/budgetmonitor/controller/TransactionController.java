package be.yorian.budgetmonitor.controller;

import be.yorian.budgetmonitor.entity.Transaction;

import java.util.List;

public interface TransactionController {

	List<Transaction> getTransactions();
    List<Transaction> getTransactionsByYear(String year);
    List<Transaction> getTransactionsByMonth(String month, String year);
    void saveTransaction(Transaction transaction);
	
}
