package com.runningapi.runningapi.dto.response.user;

import com.runningapi.runningapi.model.enums.Provider;

public record UserResponseDto(
        Long id,

        String name,

        String email,

        Provider provider
) {
}
