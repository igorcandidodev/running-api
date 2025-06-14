package com.runningapi.runningapi.dto.response.user;

import com.runningapi.runningapi.model.enums.Provider;

import java.time.LocalDate;
import java.util.List;

public record UserResponseDto(
        Long id,

        String name,

        String email,

        Provider provider,

        LocalDate birthDate,

        List<PhysicalLimitationResponseDto> physicalLimitations,

        List<PhysicalActivityResponseDto> physicalActivities
) {
}
