package be.yorian.budgetbuddy.core.command;

import be.yorian.budgetbuddy.core.port.TransactionPort;
import be.yorian.budgetbuddy.model.Transaction;
import be.yorian.budgetbuddy.repository.adapter.entity.TransactionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpdateTransactionCommandHandler extends TransactionCommandHandler {

    @Autowired
    public UpdateTransactionCommandHandler(TransactionPort transactionPort) {
        super(transactionPort);
    }

    public TransactionEntity execute(long id, Transaction transaction) {
        validateTransaction(transaction);
        return transactionPort.updateTransaction(id, transaction);
    }
}
