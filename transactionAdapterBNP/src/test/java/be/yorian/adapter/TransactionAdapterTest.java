package be.yorian.adapter;

import be.yorian.transactionAdapterBNP.adapter.TransactionAdapter;
import be.yorian.transactionAdapterBNP.adapter.TransactionAdapterFactory;
import be.yorian.transactionAdapterBNP.dto.TransactionDTO;
import be.yorian.transactionAdapterBNP.exception.TransactionAdapterException;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionAdapterTest {


    @Test
    public void convertPdfFileTest_succes() {
        TransactionAdapter transactionAdapter = TransactionAdapterFactory.createTransactionAdapter();
        List<TransactionDTO> transactions = transactionAdapter.convertPdfFile(new File("src/test/resources/testfile.pdf"));
        assertNotNull(transactions);
        assertEquals(2, transactions.size());
    }

    @Test
    public void convertPdfFileTest_noFile() {
        TransactionAdapter transactionAdapter = TransactionAdapterFactory.createTransactionAdapter();
        Exception exception = assertThrows(TransactionAdapterException.class,
                () ->transactionAdapter.convertPdfFile(new File("src/test/resources/testfile2.pdf")));

        String expectedMessage = "Something went wrong: Parse to text";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void convertPdfFileTest_noYear() {
        TransactionAdapter transactionAdapter = TransactionAdapterFactory.createTransactionAdapter();
        Exception exception = assertThrows(TransactionAdapterException.class,
                () ->transactionAdapter.convertPdfFile(new File("src/test/resources/testfile_error.pdf")));

        String expectedMessage = "Year could not be found in file";
        String actualMessage = exception.getCause().getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
