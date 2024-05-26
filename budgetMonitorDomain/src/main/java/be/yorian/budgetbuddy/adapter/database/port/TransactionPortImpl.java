package be.yorian.budgetbuddy.adapter.database.port;

import be.yorian.budgetbuddy.adapter.database.entity.TransactionEntity;
import be.yorian.budgetbuddy.adapter.database.repository.TransactionEntityRepository;
import be.yorian.budgetbuddy.core.port.TransactionPort;
import be.yorian.budgetbuddy.model.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionPortImpl implements TransactionPort {

    private final TransactionEntityRepository transactionEntityRepository;

    public TransactionPortImpl(TransactionEntityRepository transactionEntityRepository) {
        this.transactionEntityRepository = transactionEntityRepository;
    }


    @Override
    public List<Transaction> getTransactions() {
        return List.of();
    }

    @Override
    public Transaction saveTransaction(Transaction transaction) {
        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntityRepository.save(transactionEntity);
        return transaction;
    }

    @Override
    public List<Transaction> getTransactionsByMonth(String month, String year) {
        return List.of();
    }

    @Override
    public Transaction updateTransaction(Long id, Transaction transaction) {
        return null;
    }

    @Override
    public void deleteTransaction(Long id) {

    }
}
