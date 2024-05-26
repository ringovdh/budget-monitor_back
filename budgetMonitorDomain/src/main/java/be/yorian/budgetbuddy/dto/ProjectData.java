package be.yorian.budgetbuddy.dto;

import be.yorian.budgetbuddy.model.Project;

public record ProjectData(
        Project project,
        double total) { }
