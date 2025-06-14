package com.runningapi.runningapi.dto.response.user;

import com.runningapi.runningapi.model.enums.Frequency;
import com.runningapi.runningapi.model.enums.SportActivity;

public record PhysicalActivityResponseDto(
        SportActivity sportActivity,
        Frequency frequency
) {
}
