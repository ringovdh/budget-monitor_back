package be.yorian.budgetbuddy.service.impl;

import be.yorian.budgetbuddy.adapter.database.entity.TransactionEntity;
import be.yorian.budgetbuddy.adapter.database.repository.TransactionEntityRepository;
import be.yorian.budgetbuddy.core.command.CreateTransactionCommandHandler;
import be.yorian.budgetbuddy.dto.TransactionDTO;
import be.yorian.budgetbuddy.model.Transaction;
import be.yorian.budgetbuddy.service.TransactionService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static be.yorian.budgetbuddy.mapper.TransactionMapper.mapTransactionToDTO;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionEntityRepository transactionRepository;
    private final CreateTransactionCommandHandler createTransactionCommandHandler;

    @Autowired
    public TransactionServiceImpl(TransactionEntityRepository transactionRepository,
                                  CreateTransactionCommandHandler createTransactionCommandHandler) {
        this.transactionRepository = transactionRepository;
        this.createTransactionCommandHandler = createTransactionCommandHandler;
    }

    @Override
    public List<TransactionEntity> getTransactions() {
        return transactionRepository.findAll(sortByDate());
    }

    @Override
    public TransactionEntity updateTransaction(Long id, TransactionEntity updatedTransaction) {
        TransactionEntity transaction = transactionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("transaction_not_found"));

        transaction.setNumber(updatedTransaction.getNumber());
        transaction.setDate(updatedTransaction.getDate());
        transaction.setComment(updatedTransaction.getComment());
        transaction.setSign(updatedTransaction.getSign());
        transaction.setAmount(updatedTransaction.getAmount());
        transaction.setCategory(updatedTransaction.getCategory());
        transaction.setProject(updatedTransaction.getProject());

        return transactionRepository.save(transaction);
    }

    @Override
    public List<TransactionEntity> getTransactionsByMonth(String month, String year) {
        return transactionRepository.findByMonth(month, year);
    }

    @Override
    public TransactionDTO createTransaction(Transaction transaction) {
        Transaction tx = createTransactionCommandHandler.execute(transaction);
        return mapTransactionToDTO(tx);
    }

    @Override
    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }

    private Sort sortByDate() {
        return Sort.by("date").ascending();
    }

}
