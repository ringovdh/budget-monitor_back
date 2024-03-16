package be.yorian.model;

import be.yorian.transactionAdapterBNP.model.Transaction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransactionTest {

    @Test
    void convertTransactionReturnsNegativeTransactionTest() {
        Transaction tx = new Transaction("1234");
        tx.setTransactionAsText("Een negatieve transactie 009-7941410-25 Bankreferentie : 2312010527069460 01-12 1.709,83 -");
        tx.setYear("2022");
        tx.convertTransaction();

        assertEquals("-", tx.getSign());
        assertEquals(1709.83, tx.getAmount());
        assertEquals("Een negatieve transactie 009-7941410-25 Bankreferentie : 2312010527069460 ", tx.getComment());
        assertEquals("2022-12-01", tx.getDate().toString());
    }

    @Test
    void convertTransactionReturnsPositiveTransactionTest() {
        Transaction tx = new Transaction("1234");
        tx.setTransactionAsText("Een positieve transactie 009-7941410-25 Bankreferentie : 2312010527069460 01-12 709,83+");
        tx.setYear("2022");
        tx.convertTransaction();

        assertEquals("+", tx.getSign());
        assertEquals(709.83, tx.getAmount());
        assertEquals("Een positieve transactie 009-7941410-25 Bankreferentie : 2312010527069460 ", tx.getComment());
        assertEquals("2022-12-01", tx.getDate().toString());
    }
}