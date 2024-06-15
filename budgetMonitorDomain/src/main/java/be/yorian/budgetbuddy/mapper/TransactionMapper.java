package be.yorian.budgetbuddy.mapper;

import be.yorian.budgetbuddy.repository.adapter.entity.TransactionEntity;
import be.yorian.budgetbuddy.model.Transaction;
import be.yorian.transactionAdapterBNP.dto.TransactionDTO;

public final class TransactionMapper {

    public static TransactionEntity mapDTOToTransactionEntity(TransactionDTO transactionDTO) {
        TransactionEntity tx = new TransactionEntity();
        tx.setNumber(transactionDTO.number());
        tx.setOriginalComment(transactionDTO.comment());
        tx.setAmount(transactionDTO.amount());
        tx.setSign(transactionDTO.sign());
        tx.setDate(transactionDTO.date());

        return tx;
    }

    public static TransactionDTO mapTransactionToDTO(Transaction transaction) {
        return new TransactionDTO(
                transaction.getNumber(),
                transaction.getAmount(),
                transaction.getSign(),
                transaction.getDate(),
                transaction.getComment()
        );
    }

    public static be.yorian.budgetbuddy.dto.TransactionDTO mapTransactionEntityToDTO(TransactionEntity transaction) {
        return new be.yorian.budgetbuddy.dto.TransactionDTO(
                transaction.getTx_id(),
                transaction.getNumber(),
                transaction.getAmount(),
                transaction.getSign(),
                transaction.getDate(),
                transaction.getComment(),
                transaction.getCategory(),
                transaction.getProject()
        );
    }

    public static TransactionEntity mapTransactionToTransactionEntity(Transaction transaction) {
        TransactionEntity te = new TransactionEntity();
        te.setTx_id(transaction.getId());
        te.setNumber(transaction.getNumber());
        te.setAmount(transaction.getAmount());
        te.setSign(transaction.getSign());
        te.setDate(transaction.getDate());
        te.setComment(transaction.getComment());
        te.setCategory(transaction.getCategory());
        te.setProject(transaction.getProject());
        return te;
    }

    public static TransactionEntity mapTransactionToExistingTransactionEntity(Transaction transaction, TransactionEntity te) {
        te.setNumber(transaction.getNumber());
        te.setAmount(transaction.getAmount());
        te.setSign(transaction.getSign());
        te.setDate(transaction.getDate());
        te.setComment(transaction.getComment());
        te.setCategory(transaction.getCategory());
        te.setProject(transaction.getProject());
        return te;
    }

    public static Transaction mapTransactionEntityToTransaction(TransactionEntity transactionEntity) {
        Transaction tx = new Transaction();
        tx.setId(transactionEntity.getTx_id());
        tx.setNumber(transactionEntity.getNumber());
        tx.setAmount(transactionEntity.getAmount());
        tx.setSign(transactionEntity.getSign());
        tx.setDate(transactionEntity.getDate());
        tx.setComment(transactionEntity.getComment());
        tx.setCategory(transactionEntity.getCategory());
        tx.setProject(transactionEntity.getProject());
        return tx;
    }
}