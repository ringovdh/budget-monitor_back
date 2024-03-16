package be.yorian.transactionAdapterBNP.mapper;

import be.yorian.transactionAdapterBNP.model.Transaction;
import be.yorian.transactionAdapterBNP.dto.TransactionDTO;

public class TransactionMapper {

    public static TransactionDTO mapTransactionToDTO(Transaction transaction) {
        return new TransactionDTO(
                transaction.getNumber(),
                transaction.getAmount(),
                transaction.getSign(),
                transaction.getDate(),
                transaction.getComment());
    }
}
