package com.runningapi.runningapi.controllers;

import com.runningapi.runningapi.dto.PromptTemplateDto;
import com.runningapi.runningapi.model.PromptTemplate;
import com.runningapi.runningapi.service.PromptTemplateService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/prompt-templates")
public class PromptTemplateController {

    private final PromptTemplateService promptTemplateService;

    public PromptTemplateController(PromptTemplateService promptTemplateService) {
        this.promptTemplateService = promptTemplateService;
    }

    @PostMapping("/")
    @Transactional
    @Operation(summary = "Create Prompt Template")
    public ResponseEntity<PromptTemplate> createPromptTemplate(@RequestBody @Valid PromptTemplateDto promptTemplateDto, UriComponentsBuilder uriBuilder) {
        var createdPromptTemplate = promptTemplateService.createPromptTemplate(promptTemplateDto);
        var location = uriBuilder.path("prompt-templates/{id}").buildAndExpand(createdPromptTemplate.getId()).toUri();
        return ResponseEntity.created(location).body(createdPromptTemplate);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Prompt Template")
    public ResponseEntity<PromptTemplate> updatePromptTemplate(@PathVariable Long id, @RequestBody @Valid PromptTemplateDto promptTemplateDto) {
        var updatedPromptTemplate = promptTemplateService.updatePromptTemplate(id, promptTemplateDto);
        return ResponseEntity.ok(updatedPromptTemplate);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Prompt Template by ID")
    public ResponseEntity<PromptTemplate> getPromptTemplateById(@PathVariable Long id) {
        return ResponseEntity.ok(promptTemplateService.getPromptTemplateById(id));
    }

    @GetMapping
    @Operation(summary = "Get all Prompts Templates")
    public ResponseEntity<List<PromptTemplate>> getAllTasks() {
        return ResponseEntity.ok(promptTemplateService.getAllPromptsTemplates());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Prompt Template by ID")
    public ResponseEntity<PromptTemplate> deltePromptTemplate(@PathVariable Long id) {
        promptTemplateService.deletePromptTemplate(id);
        return ResponseEntity.noContent().build();
    }

}
