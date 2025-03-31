package com.runningapi.runningapi.controllers;

import com.runningapi.runningapi.service.google.GoogleAuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth/google")
public class GoogleAuthController {

    private final GoogleAuthService googleAuthService;

    public GoogleAuthController(GoogleAuthService googleAuthService) {
        this.googleAuthService = googleAuthService;
    }

    @GetMapping("/")
    public ResponseEntity<Void> getGoogleRedirectUri() {
        String redirectUri = googleAuthService.getRedirectUriAuthorization();
        return ResponseEntity.status(HttpStatus.FOUND).header("location", redirectUri).build();
    }

    @GetMapping("/callback")
    public ResponseEntity<Map<String, String>> handleGoogleCallback(@RequestParam String code) {
        try {
            googleAuthService.processAuthorizationCode(code);
            return ResponseEntity.status(HttpStatus.FOUND)
                    .header("location", "/")
                    .build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Erro ao processar o callback do Google"));
        }
    }
}