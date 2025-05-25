package com.runningapi.runningapi.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.ZonedDateTime;

public record StandardErrorResponseDto(

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
        ZonedDateTime timestamp,

        Integer status,

        String error,

        String message,

        String path
) {
}
