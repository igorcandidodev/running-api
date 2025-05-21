package com.runningapi.runningapi.controllers;

import com.runningapi.runningapi.dto.FormRequestDto;
import com.runningapi.runningapi.model.Training;
import com.runningapi.runningapi.service.FormService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/v1/form")
public class FormController {

    private final FormService formService;

    public FormController(FormService formService) {
        this.formService = formService;
    }

    @PostMapping
    @Transactional
    @Operation(summary = "Receive Forms Data")
    public ResponseEntity<List<Training>> receiveForm(@RequestBody FormRequestDto formRequestDto, UriComponentsBuilder uriBuilder) {
        var createdTrainings = formService.processForm(formRequestDto);
        var location = uriBuilder.path("/api/v1/training").build().toUri();
        return ResponseEntity.created(location).body(createdTrainings);
    }
}