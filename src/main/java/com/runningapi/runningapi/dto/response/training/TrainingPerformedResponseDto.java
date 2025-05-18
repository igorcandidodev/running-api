package com.runningapi.runningapi.dto.response.training;

import java.time.ZonedDateTime;

public record TrainingPerformedResponseDto(
        Long id,

        String title,

        Long idStrava,

        String description,

        ZonedDateTime date,

        Double distance,

        Double movingTime,

        Double elapsedTime,

        Double totalElevationGain,

        Double averageSpeed,

        Double calories
) {
}
