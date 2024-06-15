package be.yorian.budgetbuddy.core.command;

import be.yorian.budgetbuddy.exception.InvalidTransactionException;
import be.yorian.budgetbuddy.mapper.TransactionMapper;
import be.yorian.budgetbuddy.model.Transaction;
import be.yorian.budgetbuddy.repository.adapter.entity.TransactionEntity;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class UpdateTransactionCommandHandlerTest extends TransactionCommandHandlerTest {

    @InjectMocks
    UpdateTransactionCommandHandler updateTransactionCommandHandler;

    @Test
    void updateTransaction_test() {
        Transaction transaction = createTransaction();
        transaction.setId(10L);
        TransactionEntity transactionEntity = TransactionMapper.mapTransactionToTransactionEntity(transaction);

        when(transactionPort.updateTransaction(transaction.getId(), transaction)).thenReturn(transactionEntity);

        TransactionEntity createdTransaction = updateTransactionCommandHandler.execute(transaction.getId(), transaction);

        verify(transactionPort).updateTransaction(transaction.getId(), transaction);
        assertNotNull(createdTransaction);
    }

    @Test
    void updateTransactionWithoutNumber_test() {
        Transaction transaction = createTransaction();
        transaction.setId(10L);
        transaction.setNumber("");

        verify(transactionPort, never()).updateTransaction(transaction.getId(), transaction);

        assertThrows(InvalidTransactionException.class, () -> updateTransactionCommandHandler.execute(transaction.getId(), transaction));
    }

}