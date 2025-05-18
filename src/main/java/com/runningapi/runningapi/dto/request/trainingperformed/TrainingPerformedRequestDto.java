package com.runningapi.runningapi.dto.request.trainingperformed;


import java.time.ZonedDateTime;

public record TrainingPerformedRequestDto(
        String title,

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
