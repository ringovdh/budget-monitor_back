package be.yorian.budgetmonitor.dto;

import java.util.List;

public class BudgetOverviewPerYear {

    private int month;
    private List<TransactionsPerCategory> transactionsPerMonth;

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public List<TransactionsPerCategory> getTransactionsPerMonth() {
        return transactionsPerMonth;
    }

    public void setTransactionsPerMonth(List<TransactionsPerCategory> transactionsPerMonth) {
        this.transactionsPerMonth = transactionsPerMonth;
    }

}
