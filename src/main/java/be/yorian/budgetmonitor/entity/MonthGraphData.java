package be.yorian.budgetmonitor.entity;

import java.util.List;
import java.util.Map;

public record MonthGraphData (
        List<Integer> days,
        Map<Integer, Double> incomingAmounts,
        Map<Integer, Double> fixedCostAmounts,
        Map<Integer, Double> otherCostAmounts) { }
