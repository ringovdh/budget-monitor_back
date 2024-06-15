package be.yorian.budgetbuddy.core.command;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

class DeleteTransactionCommandHandlerTest extends TransactionCommandHandlerTest {

    @InjectMocks
    DeleteTransactionCommandHandler deleteTransactionCommandHandler;


    @Test
    void deleteTransaction_test() {
        Long id = 1L;
        doNothing().when(transactionPort).deleteTransaction(id);

        deleteTransactionCommandHandler.execute(id);

        verify(transactionPort).deleteTransaction(id);
    }
}