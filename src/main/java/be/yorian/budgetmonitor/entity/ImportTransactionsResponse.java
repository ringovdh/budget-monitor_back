package be.yorian.budgetmonitor.entity;

import be.yorian.budgetmonitor.dto.BudgetOverviewPerMonth;

import java.util.List;

public class ImportTransactionsResponse {

    private List<Transaction> newTransactions;
    private List<BudgetOverviewPerMonth> existingTransactions;


    public List<Transaction> getNewTransactions() {
        return newTransactions;
    }

    public void setNewTransactions(List<Transaction> newTransactions) {
        this.newTransactions = newTransactions;
    }


    public List<BudgetOverviewPerMonth> getExistingTransactions() {
        return existingTransactions;
    }

    public void setExistingTransactions(List<BudgetOverviewPerMonth> existingTransactions) {
        this.existingTransactions = existingTransactions;
    }

}
