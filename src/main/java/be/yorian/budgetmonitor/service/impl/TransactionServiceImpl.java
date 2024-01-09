package be.yorian.budgetmonitor.service.impl;

import be.yorian.budgetmonitor.entity.Transaction;
import be.yorian.budgetmonitor.repository.TransactionRepository;
import be.yorian.budgetmonitor.service.TransactionService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;


    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public List<Transaction> getTransactions() {
        return transactionRepository.findAll(sortByDate());
    }

    @Override
    public Transaction updateTransaction(Long id, Transaction updatedTransaction) {
        Transaction transaction = transactionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("transaction_not_found"));

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
    public List<Transaction> getTransactionsByMonth(String month, String year) {
        return transactionRepository.findByMonth(month, year);
    }

    @Override
    public Transaction saveTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }

    private Sort sortByDate() {
        return Sort.by("date").ascending();
    }

}
