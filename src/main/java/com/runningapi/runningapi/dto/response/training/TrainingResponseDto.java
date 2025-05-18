package com.runningapi.runningapi.dto.response.training;

import com.runningapi.runningapi.model.enums.StatusActivity;

import java.time.LocalDate;

public record TrainingResponseDto(
        Long id,

        String title,

        String description,

        Integer weekNumber,

        LocalDate date,

        String weekDay,

        StatusActivity statusActivity,

        TrainingPerformedResponseDto trainingPerformed,

        TrainingObjectiveResponseDto objective
) {
}
