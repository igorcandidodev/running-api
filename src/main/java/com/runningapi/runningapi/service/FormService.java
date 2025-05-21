package com.runningapi.runningapi.service;

import com.runningapi.runningapi.dto.FormRequestDto;
import com.runningapi.runningapi.model.*;
import com.runningapi.runningapi.repository.ObjectiveRepository;
import com.runningapi.runningapi.repository.PhysicalActivityRepository;
import com.runningapi.runningapi.repository.PhysicalLimitationRepository;
import com.runningapi.runningapi.repository.RunningActivityRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class FormService {

    private final ObjectiveRepository objectiveRepository;
    private final PhysicalActivityRepository physicalActivityRepository;
    private final RunningActivityRepository runningActivityRepository;
    private final PhysicalLimitationRepository physicalLimitationRepository;
    private final TrainingService trainingService;

    public FormService(
            ObjectiveRepository objectiveRepository,
            PhysicalActivityRepository physicalActivityRepository,
            RunningActivityRepository runningActivityRepository,
            PhysicalLimitationRepository physicalLimitationRepository,
            TrainingService trainingService
    ) {
        this.objectiveRepository = objectiveRepository;
        this.physicalActivityRepository = physicalActivityRepository;
        this.runningActivityRepository = runningActivityRepository;
        this.physicalLimitationRepository = physicalLimitationRepository;
        this.trainingService = trainingService;
    }

    @Transactional
    public List<Training> processForm(FormRequestDto formRequestDto) {
        objectiveRepository.save(new Objective(formRequestDto.objective()));
        physicalActivityRepository.save(new PhysicalActivity(formRequestDto.physicalActivity()));
        physicalLimitationRepository.save(new PhysicalLimitation(formRequestDto.physicalLimitation()));

        List<RunningActivity> runningActivities = formRequestDto.runningActivity().stream()
                .filter(Objects::nonNull)
                .map(RunningActivity::new)
                .toList();
        runningActivityRepository.saveAll(runningActivities);

        return trainingService.createTrainings(1L);
    }
}