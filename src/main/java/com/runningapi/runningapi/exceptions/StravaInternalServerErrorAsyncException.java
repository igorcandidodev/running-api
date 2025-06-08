package com.runningapi.runningapi.exceptions;

public class StravaInternalServerErrorAsyncException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    public StravaInternalServerErrorAsyncException(String message) {
        super(message);
    }

    public StravaInternalServerErrorAsyncException(String message, Throwable cause) {
        super(message, cause);
    }

    public StravaInternalServerErrorAsyncException(Throwable cause) {
        super(cause);
    }
}
