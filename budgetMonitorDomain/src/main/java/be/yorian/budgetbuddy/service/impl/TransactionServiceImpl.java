package be.yorian.budgetbuddy.service.impl;

import be.yorian.budgetbuddy.core.command.CreateTransactionCommandHandler;
import be.yorian.budgetbuddy.core.command.DeleteTransactionCommandHandler;
import be.yorian.budgetbuddy.core.command.UpdateTransactionCommandHandler;
import be.yorian.budgetbuddy.dto.TransactionDTO;
import be.yorian.budgetbuddy.model.Transaction;
import be.yorian.budgetbuddy.repository.adapter.entity.TransactionEntity;
import be.yorian.budgetbuddy.repository.adapter.repository.TransactionEntityRepository;
import be.yorian.budgetbuddy.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static be.yorian.budgetbuddy.mapper.TransactionMapper.mapTransactionEntityToDTO;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionEntityRepository transactionRepository;
    private final CreateTransactionCommandHandler createTransactionCommandHandler;
    private final DeleteTransactionCommandHandler deleteTransactionCommandHandler;
    private final UpdateTransactionCommandHandler updateTransactionCommandHandler;


    @Autowired
    public TransactionServiceImpl(TransactionEntityRepository transactionRepository,
                                  CreateTransactionCommandHandler createTransactionCommandHandler,
                                  DeleteTransactionCommandHandler deleteTransactionCommandHandler,
                                  UpdateTransactionCommandHandler updateTransactionCommandHandler) {
        this.transactionRepository = transactionRepository;
        this.createTransactionCommandHandler = createTransactionCommandHandler;
        this.deleteTransactionCommandHandler = deleteTransactionCommandHandler;
        this.updateTransactionCommandHandler = updateTransactionCommandHandler;
    }

    @Override
    public TransactionDTO updateTransaction(long id, Transaction transaction) {
        TransactionEntity tx = updateTransactionCommandHandler.execute(id, transaction);
        return mapTransactionEntityToDTO(tx);
    }

    @Override
    public List<TransactionEntity> getTransactionsByMonth(String month, String year) {
        return transactionRepository.findByMonth(month, year);
    }

    @Override
    public TransactionDTO createTransaction(Transaction transaction) {
        TransactionEntity tx = createTransactionCommandHandler.execute(transaction);
        return mapTransactionEntityToDTO(tx);
    }

    @Override
    public void deleteTransaction(long id) {
        deleteTransactionCommandHandler.execute(id);
    }

}
