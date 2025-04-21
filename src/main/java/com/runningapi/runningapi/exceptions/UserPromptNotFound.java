package com.runningapi.runningapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserPromptNotFound extends RuntimeException {
    public UserPromptNotFound() {
        super("Updated user prompt is empty. Problem with getUpdatedUserPrompt method.");
    }
}