package be.yorian.budgetmonitor.dto;

import be.yorian.budgetmonitor.entity.Project;

public record ProjectData(
        Project project,
        double total) { }
