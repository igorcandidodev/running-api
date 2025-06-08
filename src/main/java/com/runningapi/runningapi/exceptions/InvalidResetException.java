package com.runningapi.runningapi.exceptions;

public class InvalidResetException extends RuntimeException {
    public InvalidResetException(String message) {
        super(message);
    }
}