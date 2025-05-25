package com.runningapi.runningapi.dto.request.token;

public record LoginAuthRequestDto (
        String email,
        String password
){
}
