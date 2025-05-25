package com.runningapi.runningapi.controllers;

import com.runningapi.runningapi.dto.request.token.LoginAuthRequestDto;
import com.runningapi.runningapi.dto.response.token.LoginAuthResponseDto;
import com.runningapi.runningapi.security.JWTToken;
import com.runningapi.runningapi.security.UserDetailsSecurity;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth/local")
public class LocalAuthController {

    private final JWTToken jwtToken;
    private final AuthenticationManager authenticationManager;

    public LocalAuthController(JWTToken jwtToken, AuthenticationManager authenticationManager) {
        this.jwtToken = jwtToken;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    @Operation(summary = "Login with email and password")
    public ResponseEntity<LoginAuthResponseDto> login(@RequestBody LoginAuthRequestDto loginAuthRequestDto) {
        var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginAuthRequestDto.email(), loginAuthRequestDto.password());
        var auth = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        if (auth == null || !auth.isAuthenticated()) {
            throw new RuntimeException("Authentication failed");
        }
        var token = jwtToken.generateToken( (UserDetailsSecurity) auth.getPrincipal());
        var response = new LoginAuthResponseDto(token);

        return ResponseEntity.ok(response);
    }
}
