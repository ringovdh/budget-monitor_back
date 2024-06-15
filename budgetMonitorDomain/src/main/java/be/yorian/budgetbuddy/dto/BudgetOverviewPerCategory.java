package be.yorian.budgetbuddy.dto;

import be.yorian.budgetbuddy.model.Category;
import be.yorian.budgetbuddy.repository.adapter.entity.TransactionEntity;

import java.util.List;

public class BudgetOverviewPerCategory {

    private int year;
    private Category category;
    private List<TransactionEntity> transactions;
    private double total;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

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
        double positive = transactionEntities.stream().filter(transaction -> transaction.getSign().equals("+")).mapToDouble(TransactionEntity::getAmount).sum();
        double negative = transactionEntities.stream().filter(transaction -> transaction.getSign().equals("-")).mapToDouble(TransactionEntity::getAmount).sum();

        setTotal(positive - negative);
    }
}
