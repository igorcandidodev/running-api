package com.runningapi.runningapi.dto.request.user;

import jakarta.validation.constraints.Email;

public record UserRequestDto(
        String name,

        @Email
        String email,

        String password
) {
}
