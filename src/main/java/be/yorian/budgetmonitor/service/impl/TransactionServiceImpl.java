package be.yorian.budgetmonitor.service.impl;

import be.yorian.budgetmonitor.entity.Transaction;
import be.yorian.budgetmonitor.repository.TransactionRepository;
import be.yorian.budgetmonitor.service.TransactionService;
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


    private Sort sortByDate() {
        return Sort.by("date").ascending();
    }

}
