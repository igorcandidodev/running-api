package com.runningapi.runningapi.controllers;

import com.runningapi.runningapi.dto.PhysicalActivityDto;
import com.runningapi.runningapi.model.PhysicalActivity;
import com.runningapi.runningapi.service.PhysicalActivityService;
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
@RequestMapping("/physical-activity")
public class PhysicalActivityController {

    private final PhysicalActivityService physicalActivityService;

    public PhysicalActivityController(PhysicalActivityService physicalActivityService) {
        this.physicalActivityService = physicalActivityService;
    }

    @PostMapping("/")
    @Transactional
    @Operation(summary = "Create Physical Activity")
    public ResponseEntity<PhysicalActivity> createPhysicalActivity(@RequestBody @Valid PhysicalActivityDto physicalActivityDto, UriComponentsBuilder uriBuilder) {
        var createdPhysicalActivity = physicalActivityService.createPhysicalActivity(physicalActivityDto);
        var location = uriBuilder.path("physical-activity/{id}").buildAndExpand(createdPhysicalActivity.getId()).toUri();
        return ResponseEntity.created(location).body(createdPhysicalActivity);
    }

}
