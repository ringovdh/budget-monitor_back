package be.yorian.budgetmonitor.service.impl;

import be.yorian.budgetmonitor.dto.BudgetOverviewPerCategory;
import be.yorian.budgetmonitor.dto.BudgetOverviewPerYear;
import be.yorian.budgetmonitor.dto.MonthlyBudgetOverview;
import be.yorian.budgetmonitor.dto.TransactionsPerCategory;
import be.yorian.budgetmonitor.entity.Category;
import be.yorian.budgetmonitor.entity.MonthGraphData;
import be.yorian.budgetmonitor.entity.Transaction;
import be.yorian.budgetmonitor.repository.TransactionRepository;
import be.yorian.budgetmonitor.service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

@Service
public class BudgetServiceImpl implements BudgetService {

    private final TransactionRepository transactionRepository;

    @Autowired
    public BudgetServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public MonthlyBudgetOverview getBudgetOverviewPerMonth(int month, int year) {
        Map<Category, List<Transaction>> groupedByCategory = transactionRepository.findByDateContainingYearAndMonth(month, year)
                .stream().collect(groupingBy(Transaction::getCategory));
        List<TransactionsPerCategory> transactionsPerCategories = groupMonthBudgetByCategory(groupedByCategory);
        MonthGraphData graphData = retrieveMonthGroupData(groupedByCategory, month, year);
        return new MonthlyBudgetOverview(transactionsPerCategories, graphData);
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

    private List<TransactionsPerCategory> groupMonthBudgetByCategory(List<Transaction> transactions) {
        List<TransactionsPerCategory> dtos = new ArrayList<>();
        transactions.stream()
                .collect(groupingBy(Transaction::getCategory))
                .forEach((category, txs) -> {
                    TransactionsPerCategory dto = new TransactionsPerCategory();
                    dto.setCategory(category);
                    dto.setTransactions(txs);
                    dto.calculateAndSetTotal(txs);
                    dtos.add(dto);
                });
        return dtos;
    }

    private List<TransactionsPerCategory> groupMonthBudgetByCategory(Map<Category, List<Transaction>> groupedByCategory) {
        List<TransactionsPerCategory> dtos = new ArrayList<>();
        groupedByCategory.forEach((category, txs) -> {
            TransactionsPerCategory dto = new TransactionsPerCategory();
            dto.setCategory(category);
            dto.setTransactions(txs);
            dto.calculateAndSetTotal(txs);
            dtos.add(dto);
        });
        return dtos;
    }

    private MonthGraphData retrieveMonthGroupData(Map<Category, List<Transaction>> groupedByCategory, int month, int year) {
        List<Integer> days = fillDaysOfMonth(month, year);
        Map<Integer, Double> incommingAmounts = handleIncommingBudget(groupedByCategory, days);
        Map<Integer, Double> fixedCostAmounts = handleFixedCostBudget(groupedByCategory, days);
        Map<Integer, Double> otherCostAmounts = handleOtherCostBudget(groupedByCategory, days);

        return new MonthGraphData(days, incommingAmounts, fixedCostAmounts, otherCostAmounts);
    }

    private List<Integer> fillDaysOfMonth(int month, int year) {
        List<Integer> days = new ArrayList<>();
        int lengthOfMonth = YearMonth.of(year, month).lengthOfMonth();
        for(int i = 1; i <= lengthOfMonth; i++) {
            days.add(i);
        }
        return days;
    }

    private Map<Integer, Double> handleIncommingBudget(Map<Category, List<Transaction>> groupedByCategory,
                                       List<Integer> days) {
        Map<Integer, Double> incommingBudget = new HashMap<>();

        groupedByCategory.forEach((category, txs) -> {
            if (category.isRevenue()) {
                days.forEach(d -> {
                    txs.forEach(t -> {
                        if (d == t.getDate().getDayOfMonth()) {
                            if (incommingBudget.containsKey(d)) {
                                incommingBudget.merge(d, t.amount, Double::sum);
                            } else {
                                incommingBudget.put(d, t.amount);
                            }
                        } else {
                            if (!incommingBudget.containsKey(d)) {
                                incommingBudget.put(d, 0.0);
                            }
                        }
                    });
                });
            }
        });
        incommingBudget.forEach((day, amount) -> {
            if (day != 1) {
                incommingBudget.merge(day, incommingBudget.get(day-1), Double::sum);
            }
        });

        return incommingBudget;
    }

    private Map<Integer, Double> handleFixedCostBudget(Map<Category, List<Transaction>> groupedByCategory,
                                                       List<Integer> days) {
        Map<Integer, Double> fixedCostBudget = new HashMap<>();

        groupedByCategory.forEach((category, txs) -> {
            if (category.isFixedcost()) {
                days.forEach(d -> {
                    txs.forEach(t -> {
                        if (d == t.getDate().getDayOfMonth()) {
                            double amount = t.sign.equals("+") ? t.amount : 0.0-t.amount;
                            if (fixedCostBudget.containsKey(d)) {
                                fixedCostBudget.merge(d, -amount, Double::sum);
                            } else {
                                fixedCostBudget.put(d, -amount);
                            }
                        } else {
                            if (!fixedCostBudget.containsKey(d)) {
                                fixedCostBudget.put(d, 0.0);
                            }
                        }
                    });
                });
            }
        });
        fixedCostBudget.forEach((day, amount) -> {
            if (day != 1) {
                fixedCostBudget.merge(day, fixedCostBudget.get(day-1), Double::sum);
            }
        });

        return fixedCostBudget;
    }

    private Map<Integer, Double> handleOtherCostBudget(Map<Category, List<Transaction>> groupedByCategory,
                                                       List<Integer> days) {
        Map<Integer, Double> otherCostBudget = new HashMap<>();

        groupedByCategory.forEach((category, txs) -> {
            if (!category.isFixedcost() && !category.saving && !category.isRevenue()) {
                days.forEach(d -> {
                    txs.forEach(t -> {
                        double amount = t.sign.equals("+") ? t.amount : 0.0 - t.amount;
                        if (d == t.getDate().getDayOfMonth()) {
                            if (otherCostBudget.containsKey(d)) {
                                otherCostBudget.merge(d, -amount, Double::sum);
                            } else {
                                otherCostBudget.put(d, -amount);
                            }
                        } else {
                            if (!otherCostBudget.containsKey(d)) {
                                otherCostBudget.put(d, 0.0);
                            }
                        }
                    });
                });
            }
        });
        otherCostBudget.forEach((day, amount) -> {
            if (day != 1) {
                otherCostBudget.merge(day, otherCostBudget.get(day-1), Double::sum);
            }
        });

        return otherCostBudget;
    }

}
