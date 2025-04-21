package com.runningapi.runningapi.dto;

import java.time.LocalDate;

public record TrainingDto(
        String title,
        String description,
        LocalDate date,
        String weekDay
) {
}