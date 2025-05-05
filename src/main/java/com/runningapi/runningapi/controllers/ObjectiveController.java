package com.runningapi.runningapi.controllers;

import com.runningapi.runningapi.dto.ObjectiveDto;
import com.runningapi.runningapi.model.Objective;
import com.runningapi.runningapi.service.ObjectiveService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/objectives")
public class ObjectiveController {

    private final ObjectiveService objectiveService;

    public ObjectiveController(ObjectiveService objectiveService) {
        this.objectiveService = objectiveService;
    }

    @PostMapping("/")
    @Transactional
    @Operation(summary = "Create Objective")
    public ResponseEntity<Objective> createObjective(@RequestBody @Valid ObjectiveDto objectiveDto, UriComponentsBuilder uriBuilder) {
        var createdObjective = objectiveService.createObjective(objectiveDto);
        var location = uriBuilder.path("objectives/{id}").buildAndExpand(createdObjective.getId()).toUri();
        return ResponseEntity.created(location).body(createdObjective);
    }
}