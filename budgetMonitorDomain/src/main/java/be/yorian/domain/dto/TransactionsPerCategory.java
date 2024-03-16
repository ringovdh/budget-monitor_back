package be.yorian.domain.dto;

import be.yorian.domain.entity.Category;
import be.yorian.domain.entity.Transaction;

import java.util.List;

public class TransactionsPerCategory {

    private Category category;
    private List<Transaction> transactions;
    private double total;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void calculateAndSetTotal(List<Transaction> transactions) {
        setTotal(transactions.stream()
                .mapToDouble(Transaction::getAmountWithSign)
                .sum());
    }
}
