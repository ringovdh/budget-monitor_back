package be.yorian.domain.mapper;

import be.yorian.domain.entity.Transaction;
import be.yorian.transactionAdapterBNP.dto.TransactionDTO;

public class TransactionMapper {

    public static Transaction mapTransaction(TransactionDTO transactionDTO) {
        Transaction tx = new Transaction();
        tx.setNumber(transactionDTO.number());
        tx.setOriginalComment(transactionDTO.comment());
        tx.setAmount(transactionDTO.amount());
        tx.setSign(transactionDTO.sign());
        tx.setDate(transactionDTO.date());

        return tx;
    }
}
