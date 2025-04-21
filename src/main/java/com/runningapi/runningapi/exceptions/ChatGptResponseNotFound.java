package com.runningapi.runningapi.exceptions;

public class ChatGptResponseNotFound extends RuntimeException {
    public ChatGptResponseNotFound() {
        super("ChatGPT response not found. Problem with sendPrompt method.");
    }
}