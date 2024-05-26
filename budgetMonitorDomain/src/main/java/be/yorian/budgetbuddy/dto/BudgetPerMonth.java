package be.yorian.budgetbuddy.dto;

import be.yorian.budgetbuddy.adapter.database.entity.TransactionEntity;

import java.util.List;

public record BudgetPerMonth(
        int month,
        double totalIncomingBudget,
        double totalFixedOutgoingBudget,
        double totalOutgoingBudget,
        double totalSavings,
        List<TransactionEntity> transactions) { }
