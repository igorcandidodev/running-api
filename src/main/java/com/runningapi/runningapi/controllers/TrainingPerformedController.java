package com.runningapi.runningapi.controllers;

import com.runningapi.runningapi.dto.request.trainingperformed.TrainingPerformedRequestDto;
import com.runningapi.runningapi.model.TrainingPerformed;
import com.runningapi.runningapi.service.TrainingPerformedService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/training-performed")
public class TrainingPerformedController {

    private final TrainingPerformedService trainingPerformedService;

    public TrainingPerformedController(TrainingPerformedService trainingPerformedService) {
        this.trainingPerformedService = trainingPerformedService;
    }

    @PostMapping("/{idTraining}")
    @Operation(summary = "Create Training Performed")
    @Transactional
    public ResponseEntity<TrainingPerformed> createTrainingPerformed(@PathVariable Long idTraining, @RequestBody TrainingPerformedRequestDto trainingPerformedRequestDto) {
        var newTrainingPerformed = trainingPerformedService.createTrainingPerformed(idTraining, trainingPerformedRequestDto);

        var uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newTrainingPerformed.getId()).toUri();

        return ResponseEntity.created(uri).body(newTrainingPerformed);
    }
}
