package com.runningapi.runningapi.mapper;

import com.runningapi.runningapi.dto.response.objective.ObjectiveResponseDto;
import com.runningapi.runningapi.model.Objective;

public interface ObjectiveMapper {

     ObjectiveResponseDto toObjectiveResponseDto(Objective objective);

}
