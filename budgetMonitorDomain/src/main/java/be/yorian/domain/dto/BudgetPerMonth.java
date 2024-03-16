package be.yorian.domain.dto;

import be.yorian.domain.entity.Transaction;

import java.util.List;

public record BudgetPerMonth(
        int month,
        double totalIncomingBudget,
        double totalFixedOutgoingBudget,
        double totalOutgoingBudget,
        double totalSavings,
        List<Transaction> transactions) { }
