package be.yorian.budgetmonitor.dto;

import be.yorian.budgetmonitor.entity.Transaction;

import java.util.List;

public class BudgetOverviewPerCategory {

    private int year;
    private String categoryLabel;
    private List<Transaction> transactions;
    private double total;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getCategoryLabel() {
        return categoryLabel;
    }

    public void setCategoryLabel(String categoryLabel) {
        this.categoryLabel = categoryLabel;
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
        double positive = transactions.stream().filter(transaction -> transaction.sign.equals("+")).mapToDouble(Transaction::getAmount).sum();
        double negative = transactions.stream().filter(transaction -> transaction.sign.equals("-")).mapToDouble(Transaction::getAmount).sum();

        setTotal(positive - negative);
    }
}
