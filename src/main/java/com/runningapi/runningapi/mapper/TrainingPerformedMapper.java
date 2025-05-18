package com.runningapi.runningapi.mapper;

import com.runningapi.runningapi.dto.request.trainingperformed.TrainingPerformedRequestDto;
import com.runningapi.runningapi.model.TrainingPerformed;

public interface TrainingPerformedMapper {
    TrainingPerformed toEntity(TrainingPerformedRequestDto trainingPerformedRequestDto);
}
