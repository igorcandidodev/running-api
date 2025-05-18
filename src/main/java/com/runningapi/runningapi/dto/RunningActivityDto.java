package com.runningapi.runningapi.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.runningapi.runningapi.utils.DurationDeserializer;
import com.runningapi.runningapi.utils.DurationSerializer;
import com.runningapi.runningapi.model.enums.Intensity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.Duration;
import java.time.LocalDate;

public record RunningActivityDto(
        @NotNull
        double distanceCovered,
        @NotNull
        @Schema(type = "string", example = "00:10:00")
        @JsonSerialize(using = DurationSerializer.class)
        @JsonDeserialize(using = DurationDeserializer.class)
        Duration timeSpent,
        LocalDate date,
        @NotNull
        Intensity intensity,
        @NotNull
        boolean feltTired,
        @NotNull
        boolean isBestResult
) {
}