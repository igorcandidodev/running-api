package com.runningapi.runningapi.dto.response.user;

public record UserResponseDto(
        Long id,

        String name,

        String email,

        String provider
) {
}
