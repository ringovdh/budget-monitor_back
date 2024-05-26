package be.yorian.budgetbuddy.core.port;

import be.yorian.budgetbuddy.model.Transaction;

import java.util.List;

public interface TransactionPort {

    List<Transaction> getTransactions();

    Transaction saveTransaction(Transaction transaction);

    List<Transaction> getTransactionsByMonth(String month, String year);

    Transaction updateTransaction(Long id, Transaction transaction);

    void deleteTransaction(Long id);
}
