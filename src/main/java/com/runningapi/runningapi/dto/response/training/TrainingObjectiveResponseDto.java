package com.runningapi.runningapi.dto.response.training;

import com.runningapi.runningapi.model.enums.StatusObjective;

public record TrainingObjectiveResponseDto(
        String title,

        StatusObjective status
) {
}
