package be.yorian.budgetbuddy.dto;

import be.yorian.budgetbuddy.adapter.database.entity.TransactionEntity;
import be.yorian.budgetbuddy.model.Category;

import java.util.List;

public class TransactionsPerCategory {

    private Category category;
    private List<TransactionEntity> transactions;
    private double total;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<TransactionEntity> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionEntity> transactionEntities) {
        this.transactions = transactionEntities;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void calculateAndSetTotal(List<TransactionEntity> transactionEntities) {
        setTotal(transactionEntities.stream()
                .mapToDouble(TransactionEntity::getAmountWithSign)
                .sum());
    }
}
