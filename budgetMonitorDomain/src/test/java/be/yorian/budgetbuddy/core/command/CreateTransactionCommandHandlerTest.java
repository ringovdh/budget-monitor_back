package be.yorian.budgetbuddy.core.command;

import be.yorian.budgetbuddy.exception.InvalidTransactionException;
import be.yorian.budgetbuddy.mapper.TransactionMapper;
import be.yorian.budgetbuddy.model.Transaction;
import be.yorian.budgetbuddy.repository.adapter.entity.TransactionEntity;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CreateTransactionCommandHandlerTest extends TransactionCommandHandlerTest {

    @InjectMocks
    CreateTransactionCommandHandler createTransactionCommandHandler;

    @Test
    void createTransaction_test() {
        Transaction transaction = createTransaction();
        TransactionEntity transactionEntity = TransactionMapper.mapTransactionToTransactionEntity(transaction);

        when(transactionPort.saveTransaction(transaction)).thenReturn(transactionEntity);

        TransactionEntity createdTransaction = createTransactionCommandHandler.execute(transaction);

        verify(transactionPort).saveTransaction(transaction);
        assertNotNull(createdTransaction);
    }

    @Test
    void createTransactionWithoutNumber_test() {
        Transaction transaction = createTransaction();
        transaction.setNumber("");

        verify(transactionPort, never()).saveTransaction(transaction);

        assertThrows(InvalidTransactionException.class, () -> createTransactionCommandHandler.execute(transaction));
    }

}