package be.yorian.budgetmonitor.controller.impl;

import be.yorian.budgetmonitor.controller.TransactionController;
import be.yorian.budgetmonitor.entity.Transaction;
import be.yorian.budgetmonitor.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class TransactionControllerImpl implements TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionControllerImpl(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Override
    @GetMapping("/transactions")
    public List<Transaction> getTransactions() {
        return transactionService.getTransactions();
    }

    @Override
    @GetMapping("/transactions/{year}")
    public List<Transaction> getTransactionsByYear(@PathVariable String year) {
        return transactionService.getTransactionsByYear(year);
    }

    @Override
    @GetMapping("/transactions/{month}/{year}")
    public List<Transaction> getTransactionsByMonth(@PathVariable String month, @PathVariable String year) {
        return transactionService.getTransactionsByMonth(month, year);
    }

	@Override
    @PostMapping("/transactions")
    public void saveTransaction(@RequestBody Transaction transaction) {
        transactionService.saveTransaction(transaction);
    }

}
