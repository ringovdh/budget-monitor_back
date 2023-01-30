package be.yorian.budgetmonitor.service.impl;

import be.yorian.budgetmonitor.dto.BudgetOverviewPerCategory;
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
    public List<BudgetOverviewPerCategory> getBudgetOverviewPerCategory(int month, int year) {
        List <BudgetOverviewPerCategory> dtos = new ArrayList<>();

        transactionRepository.findByDateContainingYearAndMont(month, year).stream()
                .collect(groupingBy(Transaction::getCategory))
                .forEach((category, transactions) -> {
                    BudgetOverviewPerCategory dto = new BudgetOverviewPerCategory();
                    dto.setCategory(category);
                    dto.setTransactions(transactions);
                    dto.calculateAndSetTotal(transactions);
                    dtos.add(dto);
                });

        return dtos;
    }

}
