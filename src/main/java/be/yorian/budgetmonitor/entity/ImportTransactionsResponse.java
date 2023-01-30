package be.yorian.budgetmonitor.entity;

import be.yorian.budgetmonitor.dto.BudgetOverviewPerCategory;

import java.util.List;

public class ImportTransactionsResponse {

    private List<Transaction> newTransactions;
    private List<BudgetOverviewPerCategory> existingTransactions;


    public List<Transaction> getNewTransactions() {
        return newTransactions;
    }

    public void setNewTransactions(List<Transaction> newTransactions) {
        this.newTransactions = newTransactions;
    }


    public List<BudgetOverviewPerCategory> getExistingTransactions() {
        return existingTransactions;
    }

    public void setExistingTransactions(List<BudgetOverviewPerCategory> existingTransactions) {
        this.existingTransactions = existingTransactions;
    }

}
