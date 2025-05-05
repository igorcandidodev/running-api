package com.runningapi.runningapi.dto;

import com.runningapi.runningapi.enums.Frequency;
import com.runningapi.runningapi.enums.SportActivity;
import jakarta.validation.constraints.NotNull;

public record PhysicalActivityDto(
        @NotNull
        SportActivity sportActivity,
        @NotNull
        Frequency frequency
) {
}
