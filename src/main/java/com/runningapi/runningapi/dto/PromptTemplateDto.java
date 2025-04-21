package com.runningapi.runningapi.dto;

import jakarta.validation.constraints.NotBlank;

public record PromptTemplateDto(
        @NotBlank
        String name,
        @NotBlank
        String systemPrompt,
        @NotBlank
        String userPrompt
) {
}
