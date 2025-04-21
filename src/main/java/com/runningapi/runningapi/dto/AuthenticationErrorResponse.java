package com.runningapi.runningapi.dto;


public record AuthenticationErrorResponse(
        String message,
        String error,
        int status
) {
}
