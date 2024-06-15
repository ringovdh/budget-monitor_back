package be.yorian.budgetbuddy.repository.adapter.port;

import be.yorian.budgetbuddy.core.port.TransactionPort;
import be.yorian.budgetbuddy.model.Transaction;
import be.yorian.budgetbuddy.repository.adapter.entity.TransactionEntity;
import be.yorian.budgetbuddy.repository.adapter.repository.TransactionEntityRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

import static be.yorian.budgetbuddy.mapper.TransactionMapper.mapTransactionToExistingTransactionEntity;
import static be.yorian.budgetbuddy.mapper.TransactionMapper.mapTransactionToTransactionEntity;

@Service
public class TransactionPortImpl implements TransactionPort {

    private final TransactionEntityRepository transactionEntityRepository;

    public TransactionPortImpl(TransactionEntityRepository transactionEntityRepository) {
        this.transactionEntityRepository = transactionEntityRepository;
    }

    @Override
    public TransactionEntity saveTransaction(Transaction transaction) {
        TransactionEntity transactionEntity = mapTransactionToTransactionEntity(transaction);
        transactionEntityRepository.save(transactionEntity);
        return transactionEntity;
    }

    @Override
    public List<TransactionEntity> getTransactionsByMonth(String month, String year) {
        return List.of();
    }

    @Override
    public TransactionEntity updateTransaction(Long id, Transaction updatedTransaction) {
        TransactionEntity transaction = transactionEntityRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("transaction_not_found"));
        return transactionEntityRepository.save(mapTransactionToExistingTransactionEntity(updatedTransaction, transaction));
    }

    @Override
    public void deleteTransaction(Long id) {
        transactionEntityRepository.deleteById(id);
    }
}
