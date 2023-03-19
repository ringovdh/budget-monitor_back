package be.yorian.budgetmonitor.service.impl;

import be.yorian.budgetmonitor.dto.BudgetOverviewPerCategory;
import be.yorian.budgetmonitor.dto.BudgetOverviewPerMonth;
import be.yorian.budgetmonitor.dto.BudgetOverviewPerYear;
import be.yorian.budgetmonitor.entity.Transaction;
import be.yorian.budgetmonitor.repository.TransactionRepository;
import be.yorian.budgetmonitor.service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.groupingBy;

@Service
public class BudgetServiceImpl implements BudgetService {

    private final TransactionRepository transactionRepository;

    @Autowired
    public BudgetServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public List<BudgetOverviewPerMonth> getBudgetOverviewPerMonth(int month, int year) {
        return groupMonthBudgetByCategory(transactionRepository.findByDateContainingYearAndMonth(month, year));
    }

    @Override
    public List<BudgetOverviewPerCategory> getBudgetOverviewPerCategory(Long categoryId, int year) {
        List <BudgetOverviewPerCategory> dtos = new ArrayList<>();
        List <Transaction> transactions;
        if (year == 0) {
            transactions = transactionRepository.findByCategoryId(categoryId);
        } else {
            transactions = transactionRepository.findByCategoryIdAndDateContainingYear(categoryId, year);
        }
        transactions.stream()
                .collect(groupingBy(t-> t.getDate().getYear()))
                .forEach((y, txs) -> {
                    BudgetOverviewPerCategory dto = new BudgetOverviewPerCategory();
                    dto.setYear(y);
                    dto.setTransactions(txs);
                    dto.calculateAndSetTotal(txs);
                    dtos.add(dto);
                });
        return dtos;
    }

    @Override
    public List<BudgetOverviewPerYear> getBudgetOverviewPerYear(int year) {
        List <BudgetOverviewPerYear> dtos = new ArrayList<>();
        transactionRepository.findByDateContainingYear(year).stream()
                .collect(groupingBy(t -> t.getDate().getMonthValue()))
                .forEach((month, transactions) -> {
                    BudgetOverviewPerYear dto = new BudgetOverviewPerYear();
                    dto.setMonth(month);
                    dto.setTransactionsPerMonth(groupMonthBudgetByCategory(transactions));
                    dtos.add(dto);
                });

        return dtos;
    }

    private List<BudgetOverviewPerMonth> groupMonthBudgetByCategory(List<Transaction> transactions) {
        List<BudgetOverviewPerMonth> dtos = new ArrayList<>();
        transactions.stream()
                .collect(groupingBy(Transaction::getCategory))
                .forEach((category, txs) -> {
                    BudgetOverviewPerMonth dto = new BudgetOverviewPerMonth();
                    dto.setCategory(category);
                    dto.setTransactions(txs);
                    dto.calculateAndSetTotal(txs);
                    dtos.add(dto);
                });
        return dtos;
    }
}
