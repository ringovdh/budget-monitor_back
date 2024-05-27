package be.yorian.budgetbuddy.mapper;

import be.yorian.budgetbuddy.adapter.database.entity.TransactionEntity;
import be.yorian.budgetbuddy.model.Transaction;
import be.yorian.transactionAdapterBNP.dto.TransactionDTO;

public final class TransactionMapper {

    public static TransactionEntity mapTransaction(TransactionDTO transactionDTO) {
        TransactionEntity tx = new TransactionEntity();
        tx.setNumber(transactionDTO.number());
        tx.setOriginalComment(transactionDTO.comment());
        tx.setAmount(transactionDTO.amount());
        tx.setSign(transactionDTO.sign());
        tx.setDate(transactionDTO.date());

        return tx;
    }

    public static be.yorian.budgetbuddy.dto.TransactionDTO mapTransactionToDTO(Transaction transaction) {
        return new be.yorian.budgetbuddy.dto.TransactionDTO(
                transaction.number,
                transaction.amount,
                transaction.sign,
                transaction.date,
                transaction.comment
        );
    }

    public static TransactionEntity mapToEntity(Transaction transaction) {
        TransactionEntity te = new TransactionEntity();
        te.number = transaction.getNumber();
        te.amount = transaction.getAmount();
        te.sign = transaction.getSign();
        te.date = transaction.getDate();
        te.comment = transaction.getComment();
        te.category = transaction.getCategory();
        te.project = transaction.getProject();
        return te;
    }
}
