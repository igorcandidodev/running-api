package com.runningapi.runningapi.mapper.impl;

import com.runningapi.runningapi.dto.request.trainingperformed.TrainingPerformedRequestDto;
import com.runningapi.runningapi.mapper.TrainingPerformedMapper;
import com.runningapi.runningapi.model.TrainingPerformed;
import org.springframework.stereotype.Component;

@Component
public class TrainingPerformedMapperImpl implements TrainingPerformedMapper {
    @Override
    public TrainingPerformed toEntity(TrainingPerformedRequestDto trainingPerformedRequestDto) {

        var trainingPerformed = new TrainingPerformed();
        trainingPerformed.setTitle(trainingPerformedRequestDto.title());
        trainingPerformed.setDescription(trainingPerformedRequestDto.description());
        trainingPerformed.setDate(trainingPerformedRequestDto.date());
        trainingPerformed.setDistance(trainingPerformedRequestDto.distance());
        trainingPerformed.setMovingTime(trainingPerformedRequestDto.movingTime());
        trainingPerformed.setElapsedTime(trainingPerformedRequestDto.elapsedTime());
        trainingPerformed.setTotalElevationGain(trainingPerformedRequestDto.totalElevationGain());
        trainingPerformed.setAverageSpeed(trainingPerformedRequestDto.averageSpeed());

        return trainingPerformed;
    }
}
