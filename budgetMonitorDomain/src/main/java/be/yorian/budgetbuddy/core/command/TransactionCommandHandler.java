package be.yorian.budgetbuddy.core.command;

import be.yorian.budgetbuddy.exception.InvalidTransactionException;
import be.yorian.budgetbuddy.core.port.TransactionPort;
import be.yorian.budgetbuddy.model.Transaction;

public abstract class TransactionCommandHandler {

    protected TransactionPort transactionPort;

    public TransactionCommandHandler(TransactionPort transactionPort) {
        this.transactionPort = transactionPort;
    }

    protected void validateTransaction(Transaction transaction) {
        if (transaction.getNumber() == null || transaction.getNumber().isEmpty()) {
            throw new InvalidTransactionException("Transaction number cannot be empty");
        }
        if (transaction.getComment() == null || transaction.getComment().isEmpty()) {
            throw new InvalidTransactionException("Transaction comment cannot be empty");
        }
        if (transaction.getDate() == null) {
            throw new InvalidTransactionException("Transaction date cannot be null");
        }
        if (transaction.getSign() == null) {
            throw new InvalidTransactionException("Transaction sign cannot be empty");
        }
        if (transaction.getCategory() == null) {
            throw new InvalidTransactionException("Transaction category cannot be empty");
        }
    }
}
