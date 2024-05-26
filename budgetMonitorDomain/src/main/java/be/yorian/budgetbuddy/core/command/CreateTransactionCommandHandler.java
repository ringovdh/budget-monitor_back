package be.yorian.budgetbuddy.core.command;

import be.yorian.budgetbuddy.model.Transaction;
import be.yorian.budgetbuddy.core.port.TransactionPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CreateTransactionCommandHandler {

    private final TransactionPort transactionPort;

    @Autowired
    public CreateTransactionCommandHandler(TransactionPort transactionPort) {
        this.transactionPort = transactionPort;
    }


    public Transaction execute(Transaction transaction) {
        validateTransaction(transaction);
        return transactionPort.saveTransaction(transaction);
    }

    private void validateTransaction(Transaction transaction) {
        Objects.requireNonNull(transaction.number, "Transaction number cannot be empty");
        Objects.requireNonNull(transaction.comment, "Transaction comment cannot be empty");
        Objects.requireNonNull(transaction.date, "Transaction date cannot be null");
        Objects.requireNonNull(transaction.sign, "Transaction sign cannot be empty");
        Objects.requireNonNull(transaction.category, "Transaction category cannot be empty");
    }
}
