package be.yorian.domain.dto;

import be.yorian.domain.entity.Project;

public record ProjectData(
        Project project,
        double total) { }
