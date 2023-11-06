package be.yorian.budgetmonitor.dto;

public record BudgetPerMonth(
        int month,
        double totalIncomingBudget,
        double totalFixedOutgoingBudget,
        double totalOutgoingBudget,
        double totalSavings) { }
