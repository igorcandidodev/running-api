package com.runningapi.runningapi.controllers;

import com.runningapi.runningapi.dto.response.training.TrainingResponseDto;
import com.runningapi.runningapi.mapper.TrainingMapper;
import com.runningapi.runningapi.model.Training;
import com.runningapi.runningapi.service.TrainingService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/training")
public class TrainingController {

    private final TrainingService trainingService;

    private final TrainingMapper trainingMapper;

    public TrainingController(TrainingService trainingService, TrainingMapper trainingMapper) {
        this.trainingService = trainingService;
        this.trainingMapper = trainingMapper;
    }

    @PostMapping("/")
    @Transactional
    @Operation(summary = "Create trainings with ChatGPT")
    public ResponseEntity<List<Training>> createTrainingsWithChatGpt(@RequestParam(defaultValue = "1") Long promptId, UriComponentsBuilder uriBuilder) {
        try {
            List<Training> trainings = trainingService.createTrainings(promptId);
            if (trainings.isEmpty()) {
                return ResponseEntity.badRequest().body(null);
            }
            var location = uriBuilder.path("/training").build().toUri();
            return ResponseEntity.created(location).body(trainings);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
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
                                                                              @RequestParam(name="endPeriod", required = false) LocalDate endDate) {

        var trainings = trainingService.getAllTrainingsForPeriod(startDate, endDate);

        if (trainings.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        var trainingResponseDtos = trainings.stream().map(trainingMapper::toTrainingResponseDtoList).toList();

        return ResponseEntity.ok(trainingResponseDtos);
    }
}