package com.runningapi.runningapi.dto.request.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserResetPasswordDto(
        @NotBlank
        String code,

        @NotBlank
        @Size(min = 8, message = "Password must be at least 8 characters long")
        @Pattern(
                regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,16}$",
                message = "Password must contain at least one digit, one lowercase letter, one uppercase letter, and be 8 to 16 characters long"
        )
        String password
) {
}
