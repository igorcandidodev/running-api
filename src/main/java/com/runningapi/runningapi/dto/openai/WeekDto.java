package com.runningapi.runningapi.dto.openai;

import com.runningapi.runningapi.dto.TrainingDto;

import java.util.List;

public record WeekDto(
        int number,
        List<TrainingDto> trainings
) {
}
