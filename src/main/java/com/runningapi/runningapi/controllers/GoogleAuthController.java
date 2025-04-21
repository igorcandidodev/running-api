package com.runningapi.runningapi.controllers;

import com.runningapi.runningapi.security.JWTToken;
import com.runningapi.runningapi.security.UserDetailsSecurity;
import com.runningapi.runningapi.service.google.GoogleAuthService;
import com.runningapi.runningapi.service.user.UserDetailsSecurityService;
import org.springframework.beans.factory.annotation.Value;
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

    private final UserDetailsSecurityService userDetailsSecurityService;

    private final JWTToken jwtToken;

    @Value("${frontend.url.redirect}")
    private String frontendUrlRedirect;

    public GoogleAuthController(GoogleAuthService googleAuthService, UserDetailsSecurityService userDetailsSecurityService, JWTToken jwtToken) {
        this.googleAuthService = googleAuthService;
        this.userDetailsSecurityService = userDetailsSecurityService;
        this.jwtToken = jwtToken;
    }

    @GetMapping("/")
    public ResponseEntity<Void> getGoogleRedirectUri() {
        String redirectUri = googleAuthService.getRedirectUriAuthorization();
        return ResponseEntity.status(HttpStatus.FOUND).header("location", redirectUri).build();
    }

    @GetMapping("/callback")
    public ResponseEntity<Map<String, String>> handleGoogleCallback(@RequestParam String code) {
        try {
            var user = googleAuthService.processAuthorizationCode(code);
            var userDetails = userDetailsSecurityService.loadUserByUsername(user.getEmail());

            var token = jwtToken.generateToken((UserDetailsSecurity) userDetails);
            return ResponseEntity.status(HttpStatus.FOUND)
                    .header("location", frontendUrlRedirect + "#" + token)
                    .build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Erro ao processar o callback do Google"));
        }
    }
}