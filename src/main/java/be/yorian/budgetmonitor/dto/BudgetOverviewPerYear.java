package be.yorian.budgetmonitor.dto;

import java.util.List;

public class BudgetOverviewPerYear {

    private int month;
    private List<BudgetOverviewPerMonth> transactionsPerMonth;

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public List<BudgetOverviewPerMonth> getTransactionsPerMonth() {
        return transactionsPerMonth;
    }

    public void setTransactionsPerMonth(List<BudgetOverviewPerMonth> transactionsPerMonth) {
        this.transactionsPerMonth = transactionsPerMonth;
    }

}
