package be.yorian.budgetmonitor.dto;

import be.yorian.budgetmonitor.entity.MonthGraphData;

import java.util.List;

public record MonthlyBudgetOverview(
        List<TransactionsPerCategory> transactionsPerCategoryList,
        MonthGraphData monthGraphData) { }
