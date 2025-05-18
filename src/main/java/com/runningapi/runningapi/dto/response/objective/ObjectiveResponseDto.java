package com.runningapi.runningapi.dto.response.objective;

import com.runningapi.runningapi.model.enums.StatusObjective;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record ObjectiveResponseDto(
        Long id,

        String title,

        Double targetDistance,

        Duration targetTime,

        LocalDateTime createdAt,

        LocalDateTime startObjective,

        LocalDate targetDate,

        StatusObjective status
) {
}
