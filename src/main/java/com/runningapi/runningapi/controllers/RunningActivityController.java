package com.runningapi.runningapi.controllers;

import com.runningapi.runningapi.dto.RunningActivityDto;
import com.runningapi.runningapi.model.RunningActivity;
import com.runningapi.runningapi.service.RunningActivityService;
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
@RequestMapping("/running-activity")
public class RunningActivityController {

    private final RunningActivityService runningActivityService;

    public RunningActivityController(RunningActivityService runningActivityService) {
        this.runningActivityService = runningActivityService;
    }

    @PostMapping("/")
    @Transactional
    @Operation(summary = "Create Running Activity")
    public ResponseEntity<RunningActivity> createRunningActivity(@RequestBody @Valid RunningActivityDto runningActivityDto, UriComponentsBuilder uriBuilder) {
        var createdRunningActivity = runningActivityService.createRunningActivity(runningActivityDto);
        var location = uriBuilder.path("running-activity/{id}").buildAndExpand(createdRunningActivity.getId()).toUri();
        return ResponseEntity.created(location).body(createdRunningActivity);
    }
}