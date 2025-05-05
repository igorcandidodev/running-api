package com.runningapi.runningapi.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.runningapi.runningapi.utils.DurationDeserializer;
import com.runningapi.runningapi.utils.DurationSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

public record ObjectiveDto(
        @NotNull
        String title,
        @NotNull
        double targetDistance,
        @NotNull
        @Schema(type = "string", example = "01:30:00")
        @JsonSerialize(using = DurationSerializer.class)
        @JsonDeserialize(using = DurationDeserializer.class)
        Duration targetTime,
        @NotNull
        LocalDate targetDate,
        @NotNull
        boolean isFirstTimeExercising,
        @NotNull
        List<String> availableTrainingDays
) {
}