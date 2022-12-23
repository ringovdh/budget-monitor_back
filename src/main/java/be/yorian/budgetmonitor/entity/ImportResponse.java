package be.yorian.budgetmonitor.entity;

import java.util.List;

public class ImportResponse {

    private List<Transaction> filteredTransactions;
    private List<Transaction> existingTransactions;
    private double availableBudget;


    public List<Transaction> getFilteredTransactions() {
        return filteredTransactions;
    }

    public void setFilteredTransactions(List<Transaction> filteredTransactions) {
        this.filteredTransactions = filteredTransactions;
    }


    public List<Transaction> getExistingTransactions() {
        return existingTransactions;
    }

    public void setExistingTransactions(List<Transaction> existingTransactions) {
        this.existingTransactions = existingTransactions;
    }

    public double getAvailableBudget() {
        return availableBudget;
    }

    public void setAvailableBudget(double availableBudget) {
        this.availableBudget = availableBudget;
    }
}
