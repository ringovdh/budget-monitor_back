package be.yorian.budgetbuddy.service.impl;

import be.yorian.budgetbuddy.adapter.database.entity.TransactionEntity;
import be.yorian.budgetbuddy.adapter.database.repository.TransactionEntityRepository;
import be.yorian.budgetbuddy.dto.BudgetOverviewPerCategory;
import be.yorian.budgetbuddy.dto.BudgetPerCategory;
import be.yorian.budgetbuddy.dto.BudgetPerMonth;
import be.yorian.budgetbuddy.dto.GraphData;
import be.yorian.budgetbuddy.dto.MonthlyBudgetOverview;
import be.yorian.budgetbuddy.dto.ProjectData;
import be.yorian.budgetbuddy.dto.SavingsData;
import be.yorian.budgetbuddy.dto.YearlyBudgetOverview;
import be.yorian.budgetbuddy.model.Category;
import be.yorian.budgetbuddy.service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

@Service
public class BudgetServiceImpl implements BudgetService {

    private final TransactionEntityRepository transactionRepository;

    @Autowired
    public BudgetServiceImpl(TransactionEntityRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public MonthlyBudgetOverview getBudgetOverviewPerMonth(int month, int year) {
        Map<Category, List<TransactionEntity>> groupedByCategory = transactionRepository.findByDateContainingYearAndMonth(month, year)
                .stream().collect(groupingBy(TransactionEntity::getCategory));
        List<BudgetPerCategory> budgetPerCategoryList = createBudgetPerCategoryList(groupedByCategory, year);
        GraphData graphData = retrieveMonthlyGraphData(groupedByCategory, month, year);
        List<TransactionEntity> transactionEntities = transactionRepository.findByDateContainingYearAndMonthAndProjectNotNull(month, year);
        List<ProjectData> projectData = retrieveProjectData(transactionEntities);
        return new MonthlyBudgetOverview(budgetPerCategoryList, graphData, projectData);
    }

    @Override
    public List<BudgetOverviewPerCategory> getBudgetOverviewPerCategory(Long categoryId, int year) {
        List <BudgetOverviewPerCategory> dtos = new ArrayList<>();
        List <TransactionEntity> transactionEntities;
        if (year == 0) {
            transactionEntities = transactionRepository.findByCategoryId(categoryId);
        } else {
            transactionEntities = transactionRepository.findByCategoryIdAndDateContainingYear(categoryId, year);
        }
        transactionEntities.stream()
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
    public YearlyBudgetOverview getBudgetOverviewPerYear(int year) {
        List<TransactionEntity> transactionEntities = transactionRepository.findByDateContainingYear(year);
        Map<Integer, List<TransactionEntity>> groupedByMonth = transactionEntities.stream()
                .collect(groupingBy(transaction -> transaction.getDate().getMonthValue()));
        List<BudgetPerMonth> budgetPerMonthList = createBudgetPerMonthList(groupedByMonth);
        GraphData graphData = retrieveYearlyGraphData(budgetPerMonthList);
        List<ProjectData> projectData = retrieveProjectData(transactionEntities);
        SavingsData savingsData = retrieveSavingsData(transactionEntities, year);
        return new YearlyBudgetOverview(budgetPerMonthList, graphData, savingsData, projectData);
    }


    private List<BudgetPerCategory> createBudgetPerCategoryList(Map<Category, List<TransactionEntity>> groupedByCategory, int year) {
        List<BudgetPerCategory> budgetPerCategoryList = new ArrayList<>();
        groupedByCategory.forEach((category, transactions) -> {
            BudgetPerCategory bpc = new BudgetPerCategory(
                    year,
                    category,
                    transactions,
                    calculateTotalForCategory(transactions)
            );
            budgetPerCategoryList.add(bpc);
        });
        return budgetPerCategoryList;
    }

    private List<BudgetPerMonth> createBudgetPerMonthList(Map<Integer, List<TransactionEntity>> groupedByMonth) {
        List<BudgetPerMonth> budgetPerMonthList = new ArrayList<>();
        groupedByMonth.forEach((month, transactions) -> {
            BudgetPerMonth bpm = new BudgetPerMonth(
                    month,
                    calculateTotalIncomingBudget(transactions),
                    calculateTotalFixedOutgoingBudget(transactions),
                    calculateTotalOutgoingBudget(transactions),
                    calculateTotalSavings(transactions),
                    transactions
            );
            budgetPerMonthList.add(bpm);
        });
        return budgetPerMonthList;
    }

    private double calculateTotalForCategory(List<TransactionEntity> transactionEntities) {
        return transactionEntities.stream()
                .mapToDouble(TransactionEntity::getAmountWithSign)
                .sum();
    }

    private double calculateTotalSavings(List<TransactionEntity> transactionEntities) {
        return 0 - transactionEntities.stream()
                .filter(tx -> tx.category.isSaving())
                .mapToDouble(TransactionEntity::getAmountWithSign)
                .sum();
    }

    private double calculateTotalOutgoingBudget(List<TransactionEntity> transactionEntities) {
        return transactionEntities.stream()
                .filter(tx -> tx.category.isOtherCost())
                .mapToDouble(TransactionEntity::getAmountWithSign)
                .sum();
    }

    private double calculateTotalFixedOutgoingBudget(List<TransactionEntity> transactionEntities) {
        return transactionEntities.stream()
                .filter(tx -> tx.category.fixedcost)
                .mapToDouble(TransactionEntity::getAmountWithSign)
                .sum();
    }

    private Double calculateTotalIncomingBudget(List<TransactionEntity> transactionEntities) {
        return transactionEntities.stream()
                .filter(tx -> tx.category.revenue)
                .mapToDouble(TransactionEntity::getAmountWithSign)
                .sum();
    }

    private GraphData retrieveMonthlyGraphData(Map<Category, List<TransactionEntity>> groupedByCategory, int month, int year) {
        List<Integer> days = fillDaysOfMonth(month, year);
        return new GraphData(
                days,
                handleIncommingBudget(groupedByCategory, days),
                handleFixedCostBudget(groupedByCategory, days),
                handleOtherCostBudget(groupedByCategory, days)
        );
    }

    private GraphData retrieveYearlyGraphData(List<BudgetPerMonth> budgetPerMonthList) {
        return new GraphData(
                fillMonths(),
                mapIncommingAmountsToGraphData(budgetPerMonthList),
                mapFixedCostAmountsToGraphData(budgetPerMonthList),
                mapOtherCostAmountsToGraphData(budgetPerMonthList));
    }

    private List<ProjectData> retrieveProjectData(List<TransactionEntity> transactionEntities) {
        List<ProjectData> projectDataList = new ArrayList<>();
        transactionEntities.stream()
                .filter(txs -> txs.getProject() != null)
                .collect(groupingBy(TransactionEntity::getProject))
                .forEach((project, txs) -> {
                    double total = txs.stream().mapToDouble(TransactionEntity::getAmountWithSign).sum();
                    projectDataList.add(new ProjectData(project, total));
                });
        return projectDataList;
    }

    private SavingsData retrieveSavingsData(List<TransactionEntity> transactionEntities, int year) {
        List<Integer> days = fillDaysOfYear(year);
        List<TransactionEntity> savings = transactionEntities.stream()
                .filter(txs -> txs.category.isSaving()).toList();

        return new SavingsData(
                days,
                mapSavingsToGraphData(savings, days));
    }

    private Map<Integer, Double> mapSavingsToGraphData(List<TransactionEntity> savings, List<Integer> days) {
        Map<Integer, Double> mappedSavings = new HashMap<>();
        days.forEach(d -> savings.forEach(t -> {
                    if (d == t.getDate().getDayOfYear()) {
                        if (mappedSavings.containsKey(d)) {
                            mappedSavings.merge(d, t.getAmountWithSign(), Double::sum);
                        } else {
                            mappedSavings.put(d, t.getAmountWithSign());
                        }
                    } else {
                        if (!mappedSavings.containsKey(d)) {
                            mappedSavings.put(d, 0.0);
                        }
                    }
                })
        );
        mergeAmountsWithPrevious(mappedSavings);
        return mappedSavings;
    }

    private Map<Integer, Double> mapOtherCostAmountsToGraphData(List<BudgetPerMonth> budgetList) {
        Map<Integer, Double> mappedBudget = new HashMap<>();
        budgetList.forEach(bpm -> mappedBudget.put(bpm.month(), -bpm.totalOutgoingBudget()));
        mergeAmountsWithPrevious(mappedBudget);
        return mappedBudget;
    }

    private Map<Integer, Double> mapFixedCostAmountsToGraphData(List<BudgetPerMonth> budgetList) {
        Map<Integer, Double> mappedBudget = new HashMap<>();
        budgetList.forEach(bpm -> mappedBudget.put(bpm.month(), -bpm.totalFixedOutgoingBudget()));
        mergeAmountsWithPrevious(mappedBudget);
        return mappedBudget;
    }

    private Map<Integer, Double> mapIncommingAmountsToGraphData(List<BudgetPerMonth> budgetList) {
        Map<Integer, Double> mappedBudget = new HashMap<>();
        budgetList.forEach(bpm -> mappedBudget.put(bpm.month(), bpm.totalIncomingBudget()));
        mergeAmountsWithPrevious(mappedBudget);
        return mappedBudget;
    }

    private void mergeAmountsWithPrevious(Map<Integer, Double> amounts) {
        amounts.forEach((key, amount) -> {
            if (key != 1) {
                amounts.merge(key, amounts.get(key-1), Double::sum);
            }
        });
    }

    private List<Integer> fillDaysOfYear(int year) {
        List<Integer> days = new ArrayList<>();
        int lengthOfYear = Year.of(year).length();
        for (int i = 1; i <= lengthOfYear; i++) {
            days.add(i);
        }
        return days;
    }

    private List<Integer> fillDaysOfMonth(int month, int year) {
        List<Integer> days = new ArrayList<>();
        int lengthOfMonth = YearMonth.of(year, month).lengthOfMonth();
        for (int i = 1; i <= lengthOfMonth; i++) {
            days.add(i);
        }
        return days;
    }

    private List<Integer> fillMonths() {
        return List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
    }

    private Map<Integer, Double> handleIncommingBudget(Map<Category, List<TransactionEntity>> groupedByCategory,
                                       List<Integer> days) {
        Map<Integer, Double> incommingBudget = new HashMap<>();

        groupedByCategory.forEach((category, txs) -> {
            if (category.isRevenue()) {
                days.forEach(d -> txs.forEach(t -> {
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
                }));
            }
        });
        incommingBudget.forEach((day, amount) -> {
            if (day != 1) {
                incommingBudget.merge(day, incommingBudget.get(day-1), Double::sum);
            }
        });

        return incommingBudget;
    }

    private Map<Integer, Double> handleFixedCostBudget(Map<Category, List<TransactionEntity>> groupedByCategory,
                                                       List<Integer> days) {
        Map<Integer, Double> fixedCostBudget = new HashMap<>();

        groupedByCategory.forEach((category, txs) -> {
            if (category.isFixedcost()) {
                days.forEach(d -> txs.forEach(t -> {
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
                }));
            }
        });
        fixedCostBudget.forEach((day, amount) -> {
            if (day != 1) {
                fixedCostBudget.merge(day, fixedCostBudget.get(day-1), Double::sum);
            }
        });

        return fixedCostBudget;
    }

    private Map<Integer, Double> handleOtherCostBudget(Map<Category, List<TransactionEntity>> groupedByCategory,
                                                       List<Integer> days) {
        Map<Integer, Double> otherCostBudget = new HashMap<>();

        groupedByCategory.forEach((category, txs) -> {
            if (!category.isFixedcost() && !category.saving && !category.isRevenue()) {
                days.forEach(d -> txs.forEach(t -> {
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
                }));
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
