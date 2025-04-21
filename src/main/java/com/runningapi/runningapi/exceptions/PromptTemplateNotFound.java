package com.runningapi.runningapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PromptTemplateNotFound extends RuntimeException {
    public PromptTemplateNotFound(Long id) {
        super("Prompt Template not found with id: " + id);
    }
}