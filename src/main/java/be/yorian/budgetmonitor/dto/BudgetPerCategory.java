package be.yorian.budgetmonitor.dto;

import be.yorian.budgetmonitor.entity.Category;
import be.yorian.budgetmonitor.entity.Transaction;

import java.util.List;

public record BudgetPerCategory(
        int year,
        Category category,
        List<Transaction> transactions,
        double total) { }
