package be.yorian.budgetmonitor.controller;

import be.yorian.budgetmonitor.entity.Transaction;

public interface TransactionController {

    Transaction saveTransaction(Transaction transaction);
    Transaction updateTransaction(Long id, Transaction transaction);
    void deleteTransaction(Long id);
	
}
