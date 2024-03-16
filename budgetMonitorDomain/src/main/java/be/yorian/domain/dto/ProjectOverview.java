package be.yorian.domain.dto;

import be.yorian.domain.entity.Project;
import be.yorian.domain.entity.Transaction;

import java.math.BigDecimal;
import java.util.List;

public record ProjectOverview(
        Project project,
        List<Transaction> transactions,
        BigDecimal projectTotal) { }
