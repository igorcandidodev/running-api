package com.runningapi.runningapi.mapper.impl;

import com.runningapi.runningapi.dto.response.training.TrainingObjectiveResponseDto;
import com.runningapi.runningapi.dto.response.training.TrainingPerformedResponseDto;
import com.runningapi.runningapi.dto.response.training.TrainingResponseDto;
import com.runningapi.runningapi.mapper.TrainingMapper;
import com.runningapi.runningapi.model.Objective;
import com.runningapi.runningapi.model.Training;
import com.runningapi.runningapi.model.TrainingPerformed;
import org.springframework.stereotype.Component;


@Component
public class TrainingMapperImpl implements TrainingMapper {

    @Override
    public TrainingResponseDto toTrainingResponseDtoList(Training trainings) {
        return new TrainingResponseDto(
                trainings.getId(),
                trainings.getTitle(),
                trainings.getDescription(),
                trainings.getWeekNumber(),
                trainings.getDate(),
                trainings.getWeekDay(),
                trainings.getStatusActivity(),
                trainings.getTrainingPerformed() != null ? mapTrainingPerformed(trainings.getTrainingPerformed()) : null,
                mapObjective(trainings.getObjective())
        );
    }

    private TrainingPerformedResponseDto mapTrainingPerformed(TrainingPerformed trainingPerformed) {
        if (trainingPerformed == null) {
            return null;
        }

        return new TrainingPerformedResponseDto(
                trainingPerformed.getId(),
                trainingPerformed.getTitle(),
                trainingPerformed.getIdStrava(),
                trainingPerformed.getDescription(),
                trainingPerformed.getDate(),
                trainingPerformed.getDistance(),
                trainingPerformed.getMovingTime(),
                trainingPerformed.getElapsedTime(),
                trainingPerformed.getTotalElevationGain(),
                trainingPerformed.getAverageSpeed(),
                trainingPerformed.getCalories()
        );
    }

    private TrainingObjectiveResponseDto mapObjective(Objective objective) {
        if (objective == null) {
            return null;
        }
        return new TrainingObjectiveResponseDto(
                objective.getTitle(),
                objective.getStatus()
        );
    }
}
