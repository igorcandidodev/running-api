package com.runningapi.runningapi.dto.response.objective;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.runningapi.runningapi.model.enums.StatusObjective;
import com.runningapi.runningapi.utils.DurationDeserializer;
import com.runningapi.runningapi.utils.DurationSerializer;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record ObjectiveResponseDto(
        Long id,

        String title,

        Double targetDistance,

        @JsonSerialize(using = DurationSerializer.class)
        @JsonDeserialize(using = DurationDeserializer.class)
        @Schema(type = "string", example = "01:30:00")
        Duration targetTime,

        LocalDateTime createdAt,

        LocalDateTime startObjective,

        LocalDate targetDate,

        StatusObjective status
) {
}
