package be.yorian.domain.dto;

import be.yorian.domain.entity.Category;
import be.yorian.domain.entity.Transaction;

import java.util.List;

public record BudgetPerCategory(
        int year,
        Category category,
        List<Transaction> transactions,
        double total) { }
