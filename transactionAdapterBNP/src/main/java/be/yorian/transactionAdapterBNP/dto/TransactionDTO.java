package be.yorian.transactionAdapterBNP.dto;

import java.time.LocalDate;

public record TransactionDTO(
        String number,
        Double amount,
        String sign,
        LocalDate date,
        String comment) { }
