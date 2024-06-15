package be.yorian.budgetbuddy.core.command;

import be.yorian.budgetbuddy.core.port.TransactionPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeleteTransactionCommandHandler extends TransactionCommandHandler {

    @Autowired
    public DeleteTransactionCommandHandler(TransactionPort transactionPort) {
        super(transactionPort);
    }

    public void execute(long id) {
        transactionPort.deleteTransaction(id);
    }
}
