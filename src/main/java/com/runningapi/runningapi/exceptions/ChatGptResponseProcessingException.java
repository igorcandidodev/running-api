package com.runningapi.runningapi.exceptions;

public class ChatGptResponseProcessingException extends RuntimeException {

    public ChatGptResponseProcessingException(String message, Throwable cause) {
        super("Erro ao processar o JSON do ChatGPT: " + message, cause);
    }
}
