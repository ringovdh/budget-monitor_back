package be.yorian.budgetmonitor.service.impl;

import be.yorian.budgetmonitor.dto.BudgetOverviewPerCategory;
import be.yorian.budgetmonitor.dto.BudgetOverviewPerMonth;
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
        List <BudgetOverviewPerMonth> dtos = new ArrayList<>();

        transactionRepository.findByDateContainingYearAndMont(month, year).stream()
                .collect(groupingBy(Transaction::getCategory))
                .forEach((category, transactions) -> {
                    BudgetOverviewPerMonth dto = new BudgetOverviewPerMonth();
                    dto.setCategory(category);
                    dto.setTransactions(transactions);
                    dto.calculateAndSetTotal(transactions);
                    dtos.add(dto);
                });

        return dtos;
    }

    @Override
    public List<BudgetOverviewPerCategory> getBudgetOverviewPerCategory(Long categoryId, int year) {
        List <BudgetOverviewPerCategory> dtos = new ArrayList<>();
        List <Transaction> transactions = new ArrayList<>();
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

}
