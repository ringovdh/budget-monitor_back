package be.yorian.budgetmonitor.dto;

import be.yorian.budgetmonitor.entity.Project;
import be.yorian.budgetmonitor.entity.Transaction;

import java.math.BigDecimal;
import java.util.List;

public record ProjectOverview(
        Project project,
        List<Transaction> transactions,
        BigDecimal projectTotal) { }
