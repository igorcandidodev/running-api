package com.runningapi.runningapi.exceptions;

public class UserNotFound extends RuntimeException {
    public UserNotFound(String email) {
        super("Prompt Template not found with email: " + email);
    }
}