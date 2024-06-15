package be.yorian.budgetbuddy.core.command;

import be.yorian.budgetbuddy.core.port.TransactionPort;
import be.yorian.budgetbuddy.model.Category;
import be.yorian.budgetbuddy.model.Transaction;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
public abstract class TransactionCommandHandlerTest {

    @Mock
    protected TransactionPort transactionPort;

    protected Transaction createTransaction() {
        Transaction transaction = new Transaction();
        transaction.setNumber("TX_001");
        transaction.setAmount(100.0);
        transaction.setSign("+");
        transaction.setDate(LocalDate.now());
        transaction.setComment("test comment");
        transaction.setCategory(createCategory());

        return transaction;
    }

    private Category createCategory() {
        Category category = new Category();
        category.setLabel("Test category");
        return category;
    }
}