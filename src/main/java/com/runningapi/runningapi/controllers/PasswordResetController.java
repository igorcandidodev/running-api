package com.runningapi.runningapi.controllers;

import com.runningapi.runningapi.dto.request.user.UserResetPasswordDto;
import com.runningapi.runningapi.service.PasswordResetService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/password-reset")
public class PasswordResetController {

    private final PasswordResetService passwordResetService;

    public PasswordResetController(PasswordResetService passwordResetService) {
        this.passwordResetService = passwordResetService;
    }

    @PostMapping("/request")
    public void requestReset(@RequestParam String email) {
        passwordResetService.requestPasswordReset(email);
    }

    @PostMapping("/reset")
    public void resetPassword(@RequestBody UserResetPasswordDto dto) {
        passwordResetService.resetPassword(dto.code(), dto.password());
    }
}