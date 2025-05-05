package com.runningapi.runningapi.controllers;

import com.runningapi.runningapi.dto.PhysicalLimitationDto;
import com.runningapi.runningapi.model.PhysicalLimitation;
import com.runningapi.runningapi.service.PhysicalLimitationService;
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
@RequestMapping("/physical-limitation")
public class PhysicalLimitationController {

    private final PhysicalLimitationService physicalLimitationService;

    public PhysicalLimitationController(PhysicalLimitationService physicalLimitationService) {
        this.physicalLimitationService = physicalLimitationService;
    }

    @PostMapping("/")
    @Transactional
    @Operation(summary = "Create Physical Limitation")
    public ResponseEntity<PhysicalLimitation> createPhysicalLimitation(@RequestBody @Valid PhysicalLimitationDto physicalLimitationDto, UriComponentsBuilder uriBuilder) {
        var createdPhysicalLimitation = physicalLimitationService.createPhysicalLimitation(physicalLimitationDto);
        var location = uriBuilder.path("physical-limitation/{id}").buildAndExpand(createdPhysicalLimitation.getId()).toUri();
        return ResponseEntity.created(location).body(createdPhysicalLimitation);
    }
}