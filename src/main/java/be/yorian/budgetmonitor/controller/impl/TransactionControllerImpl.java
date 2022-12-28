package be.yorian.budgetmonitor.controller.impl;

import be.yorian.budgetmonitor.controller.TransactionController;
import be.yorian.budgetmonitor.entity.Transaction;
import be.yorian.budgetmonitor.response.CustomResponse;
import be.yorian.budgetmonitor.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    @GetMapping(produces = "application/json", path="transactions/comment")
    public ResponseEntity<CustomResponse> getTransactionsByComment(@RequestParam Optional<String> comment,
                                                                   @RequestParam Optional<Integer> page,
                                                                   @RequestParam Optional<Integer> size) {
        Page<Transaction> transactions = transactionService.getTransactionsByComment(comment.orElse(""),
                page.orElse(0), size.orElse(10));
        CustomResponse response = new CustomResponse();
        response.setStatus(HttpStatus.OK);
        response.setStatusCode(HttpStatus.OK.value());
        Map<String, Page> dataMap = new HashMap<>();
        dataMap.put("page", transactions);
        response.setData(dataMap);
        return ResponseEntity.ok().body(response);
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

    @Override
    @PutMapping("/transactions/{id}")
    public void updateTransaction(@PathVariable("id")Long id, @RequestBody Transaction transaction) {
        transactionService.updateTransaction(id, transaction);
    }

    @Override
    @DeleteMapping("/transactions/{id}")
    public void deleteTransaction(@PathVariable("id")Long id) {
        transactionService.deleteTransaction(id);
    }

}
