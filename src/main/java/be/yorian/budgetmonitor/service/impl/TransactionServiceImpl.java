package be.yorian.budgetmonitor.service.impl;

import be.yorian.budgetmonitor.entity.Transaction;
import be.yorian.budgetmonitor.repository.TransactionRepository;
import be.yorian.budgetmonitor.service.TransactionService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.domain.PageRequest.of;

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
    public Page<Transaction> getTransactionsByComment(String comment, Integer page, Integer size){
        return transactionRepository.findByCommentContaining(comment, of(page, size));
    }

    @Override
    public void updateTransaction(Long id, Transaction updatedTransaction) {
        Optional<Transaction> optionalTransaction = transactionRepository.findById(id);
        if (optionalTransaction.isEmpty()) {
            throw new EntityNotFoundException("transaction_not_found");
        } else {
            Transaction transaction = optionalTransaction.get();
            transaction.setNumber(updatedTransaction.getNumber());
            transaction.setDate(updatedTransaction.getDate());
            transaction.setComment(updatedTransaction.getComment());
            transaction.setSign(updatedTransaction.getSign());
            transaction.setAmount(updatedTransaction.getAmount());
            transaction.setCategory(updatedTransaction.getCategory());
            transactionRepository.save(transaction);
        }
    }

    @Override
    public List<Transaction> getTransactionsByYear(String year) {
        return transactionRepository.findByYear(year);
    }

    @Override
    public List<Transaction> getTransactionsByMonth(String month, String year) {
        List<Transaction> txs = transactionRepository.findByMonth(month, year);
        return txs;
    }

    @Override
    public void saveTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    @Override
    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }

    private Sort sortByDate() {
        return Sort.by("date").ascending();
    }

}
