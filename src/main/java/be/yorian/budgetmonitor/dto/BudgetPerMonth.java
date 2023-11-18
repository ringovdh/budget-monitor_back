package be.yorian.budgetmonitor.dto;

import be.yorian.budgetmonitor.entity.Transaction;

import java.util.List;

public record BudgetPerMonth(
        int month,
        double totalIncomingBudget,
        double totalFixedOutgoingBudget,
        double totalOutgoingBudget,
        double totalSavings,
        List<Transaction> transactions) { }
