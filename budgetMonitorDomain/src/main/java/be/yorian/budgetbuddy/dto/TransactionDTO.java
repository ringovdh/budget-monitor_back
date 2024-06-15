package be.yorian.budgetbuddy.dto;

import be.yorian.budgetbuddy.model.Category;
import be.yorian.budgetbuddy.model.Project;

import java.time.LocalDate;

public record TransactionDTO(
        long tx_id,
        String number,
        Double amount,
        String sign,
        LocalDate date,
        String comment,
        Category category,
        Project project) { }
