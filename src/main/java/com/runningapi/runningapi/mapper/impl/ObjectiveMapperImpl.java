package com.runningapi.runningapi.mapper.impl;

import com.runningapi.runningapi.dto.response.objective.ObjectiveResponseDto;
import com.runningapi.runningapi.mapper.ObjectiveMapper;
import com.runningapi.runningapi.model.Objective;
import org.springframework.stereotype.Component;

@Component
public class ObjectiveMapperImpl implements ObjectiveMapper {
    @Override
    public ObjectiveResponseDto toObjectiveResponseDto(Objective objective) {
        return new ObjectiveResponseDto(
                objective.getId(),
                objective.getTitle(),
                objective.getTargetDistance(),
                objective.getTargetTime(),
                objective.getCreatedAt(),
                objective.getCreatedAt(),
                objective.getTargetDate(),
                objective.getStatus()
        );
    }
}
