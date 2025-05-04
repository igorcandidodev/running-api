package com.runningapi.runningapi.dto;

import jakarta.validation.constraints.NotNull;

public record PhysicalLimitationDto(
        @NotNull
        boolean feltPain,
        String description
) {
}