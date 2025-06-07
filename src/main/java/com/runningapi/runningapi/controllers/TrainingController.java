package com.runningapi.runningapi.controllers;

import com.runningapi.runningapi.dto.FormRequestDto;
import com.runningapi.runningapi.dto.response.training.TrainingResponseDto;
import com.runningapi.runningapi.mapper.TrainingMapper;
import com.runningapi.runningapi.model.Training;
import com.runningapi.runningapi.service.FormService;
import com.runningapi.runningapi.service.TrainingService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/training")
public class TrainingController {

    private final TrainingService trainingService;
    private final TrainingMapper trainingMapper;
    private final FormService formService;


    public TrainingController(TrainingService trainingService, TrainingMapper trainingMapper, FormService formService) {
        this.trainingService = trainingService;
        this.trainingMapper = trainingMapper;
        this.formService = formService;
    }

    @PostMapping("/")
    @Transactional
    @Operation(summary = "Create trainings with ChatGPT by forms")
    public ResponseEntity<List<Training>> createTrainingsByFormData(@RequestBody FormRequestDto formRequestDto, UriComponentsBuilder uriBuilder) {
        var createdTrainings = formService.processForm(formRequestDto);
        var ids = createdTrainings.stream()
                .map(training -> training.getId().toString())
                .collect(Collectors.joining(","));
        var location = uriBuilder.path("/api/v1/training/{ids}").buildAndExpand(ids).toUri();
        return ResponseEntity.created(location).body(createdTrainings);
    }

    @GetMapping("/")
    @Operation(summary = "Get all User's trainings")
    public ResponseEntity<List<TrainingResponseDto>> getAllTrainings() {
        var trainings = trainingService.getAllTrainings();
        if (trainings.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        var trainingResponseDtos = trainings.stream().map(trainingMapper::toTrainingResponseDtoList).toList();

        return ResponseEntity.ok(trainingResponseDtos);
    }

    @GetMapping("/period/")
    @Operation(summary = "Get all User's trainings for a specific period")
    public ResponseEntity<List<TrainingResponseDto>> getAllTrainingsForPeriod(@RequestParam("startPeriod") LocalDate startDate,
                                                                              @RequestParam(name = "endPeriod", required = false) LocalDate endDate) {

        var trainings = trainingService.getAllTrainingsForPeriod(startDate, endDate);

        if (trainings.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        var trainingResponseDtos = trainings.stream().map(trainingMapper::toTrainingResponseDtoList).toList();

        return ResponseEntity.ok(trainingResponseDtos);
    }
}