package be.yorian.budgetbuddy.model;

import be.yorian.budgetbuddy.repository.adapter.entity.TransactionEntity;
import be.yorian.budgetbuddy.dto.TransactionsPerCategory;

import java.util.List;

public class ImportTransactionsResponse {

    private List<TransactionEntity> newTransactionEntities;
    private List<TransactionsPerCategory> existingTransactions;


    public List<TransactionEntity> getNewTransactions() {
        return newTransactionEntities;
    }

    public void setNewTransactions(List<TransactionEntity> newTransactionEntities) {
        this.newTransactionEntities = newTransactionEntities;
    }


    public List<TransactionsPerCategory> getExistingTransactions() {
        return existingTransactions;
    }

    public void setExistingTransactions(List<TransactionsPerCategory> existingTransactions) {
        this.existingTransactions = existingTransactions;
    }

}
