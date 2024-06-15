package be.yorian.budgetbuddy.core.port;

import be.yorian.budgetbuddy.repository.adapter.entity.TransactionEntity;
import be.yorian.budgetbuddy.model.Transaction;

import java.util.List;

public interface TransactionPort {

    TransactionEntity saveTransaction(Transaction transaction);

    List<TransactionEntity> getTransactionsByMonth(String month, String year);

    TransactionEntity updateTransaction(Long id, Transaction transaction);

    void deleteTransaction(Long id);
}
