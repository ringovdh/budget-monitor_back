package be.yorian.budgetbuddy.service;

import be.yorian.budgetbuddy.repository.adapter.entity.TransactionEntity;
import be.yorian.budgetbuddy.dto.TransactionDTO;
import be.yorian.budgetbuddy.model.Transaction;

import java.util.List;

public interface TransactionService {

    TransactionDTO createTransaction(Transaction transaction);
    List<TransactionEntity> getTransactionsByMonth(String month, String year);
    TransactionDTO updateTransaction(long id, Transaction transaction);
    void deleteTransaction(long id);
}
