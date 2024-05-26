package be.yorian.budgetbuddy.service;

import be.yorian.budgetbuddy.adapter.database.entity.TransactionEntity;
import be.yorian.budgetbuddy.dto.TransactionDTO;
import be.yorian.budgetbuddy.model.Transaction;

import java.util.List;

public interface TransactionService {

    List<TransactionEntity> getTransactions();
    TransactionDTO createTransaction(Transaction transaction);
    List<TransactionEntity> getTransactionsByMonth(String month, String year);
    TransactionEntity updateTransaction(Long id, TransactionEntity transaction);
    void deleteTransaction(Long id);
}
