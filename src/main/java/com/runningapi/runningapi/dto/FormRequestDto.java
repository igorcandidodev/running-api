package com.runningapi.runningapi.dto;

import java.util.List;

public record FormRequestDto(
        ObjectiveDto objective,
        PhysicalActivityDto physicalActivity,
        List<RunningActivityDto> runningActivity,
        PhysicalLimitationDto physicalLimitation
) {
}
