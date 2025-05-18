package com.runningapi.runningapi.controllers;

import com.runningapi.runningapi.dto.ObjectiveDto;
import com.runningapi.runningapi.dto.response.objective.ObjectiveResponseDto;
import com.runningapi.runningapi.mapper.ObjectiveMapper;
import com.runningapi.runningapi.model.Objective;
import com.runningapi.runningapi.service.ObjectiveService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/objectives")
public class ObjectiveController {

    private final ObjectiveService objectiveService;

    private final ObjectiveMapper objectiveMapper;

    public ObjectiveController(ObjectiveService objectiveService, ObjectiveMapper objectiveMapper) {
        this.objectiveService = objectiveService;
        this.objectiveMapper = objectiveMapper;
    }

    @PostMapping("/")
    @Transactional
    @Operation(summary = "Create Objective")
    public ResponseEntity<Objective> createObjective(@RequestBody @Valid ObjectiveDto objectiveDto, UriComponentsBuilder uriBuilder) {
        var createdObjective = objectiveService.createObjective(objectiveDto);
        var location = uriBuilder.path("objectives/{id}").buildAndExpand(createdObjective.getId()).toUri();
        return ResponseEntity.created(location).body(createdObjective);
    }

    @GetMapping("/")
    @Operation(summary = "Get All User's Objectives")
    public ResponseEntity<List<ObjectiveResponseDto>> getAllObjectives() {
        var objectives = objectiveService.findAllObjectives();
        if (objectives.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        var objectivesDto = objectives.stream()
                            .map(objectiveMapper::toObjectiveResponseDto)
                            .toList();

        return ResponseEntity.ok(objectivesDto);
    }

    @DeleteMapping("/cancel/{id}")
    @Transactional
    @Operation(summary = "Cancel Objective by ID")
    public ResponseEntity<Void> cancelObjective(@PathVariable Long id) {
        objectiveService.cancelObjective(id);
        return ResponseEntity.noContent().build();
    }
}