package be.yorian.budgetbuddy.dto;

import be.yorian.budgetbuddy.repository.adapter.entity.TransactionEntity;
import be.yorian.budgetbuddy.model.Project;

import java.math.BigDecimal;
import java.util.List;

public record ProjectOverview(
        Project project,
        List<TransactionEntity> transactions,
        BigDecimal projectTotal) { }
