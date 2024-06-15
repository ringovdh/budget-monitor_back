package be.yorian.budgetbuddy.dto;

import be.yorian.budgetbuddy.repository.adapter.entity.TransactionEntity;
import be.yorian.budgetbuddy.model.Category;

import java.util.List;

public record BudgetPerCategory(
        int year,
        Category category,
        List<TransactionEntity> transactions,
        double total) { }
