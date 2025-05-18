package com.runningapi.runningapi.mapper;

import com.runningapi.runningapi.dto.response.training.TrainingResponseDto;
import com.runningapi.runningapi.model.Training;

public interface TrainingMapper {

    TrainingResponseDto toTrainingResponseDtoList(Training trainings);
}
