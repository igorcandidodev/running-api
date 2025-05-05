package com.runningapi.runningapi.controllers;

import com.runningapi.runningapi.model.Training;
import com.runningapi.runningapi.service.TrainingService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/training")
public class TrainingController {

    private final TrainingService trainingService;

    public TrainingController(TrainingService trainingService) {
        this.trainingService = trainingService;
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
}