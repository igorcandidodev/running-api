package com.runningapi.runningapi.controllers;

import com.runningapi.runningapi.service.strava.StravaServices;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth/strava")
public class StravaAuthController {

    @Autowired
    private StravaServices stravaServices;

    @GetMapping("/")
    @Operation(summary = "Redirect to Strava authorization page to integrate Strava with Moove")
    public ResponseEntity<Void> authentication() {
        return ResponseEntity.status(HttpStatus.FOUND).header("location", stravaServices.getRedirectUriAuthorization()).build();
    }

    @GetMapping("/callback")
    public ResponseEntity<Void> callback(@RequestParam String code, @RequestParam String state) {
        stravaServices.processAuthorizationCode(code, state);

        return ResponseEntity.status(HttpStatus.FOUND).header("location", "").build();
    }
}
