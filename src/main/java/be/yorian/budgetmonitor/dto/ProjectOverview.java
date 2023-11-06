package be.yorian.budgetmonitor.dto;

import be.yorian.budgetmonitor.entity.Transaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record ProjectOverview(
        long id,
        String projectname,
        String description,
        LocalDate startdate,
        LocalDate enddate,
        String icon,
        List<Transaction> transactions,
        BigDecimal projectTotal) {

}
