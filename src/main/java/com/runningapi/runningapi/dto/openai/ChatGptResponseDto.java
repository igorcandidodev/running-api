package com.runningapi.runningapi.dto.openai;

import java.util.List;

public record ChatGptResponseDto(
        List<WeekDto> weeks
) {
}
