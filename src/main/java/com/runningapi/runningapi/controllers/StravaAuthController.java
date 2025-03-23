package com.runningapi.runningapi.controllers;

import com.runningapi.runningapi.service.strava.StravaServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth/strava")
public class StravaAuthController {

    @Autowired
    private StravaServices stravaServices;

    @GetMapping("/{idUsuario}")
    public ResponseEntity<Void> authentication(@PathVariable Long idUsuario) {
        return ResponseEntity.status(HttpStatus.FOUND).header("location", stravaServices.getRedirectUriAuthorization(idUsuario)).build();
    }

    @GetMapping("/callback")
    public ResponseEntity<Void> callback(@RequestParam String code, @RequestParam Long state) {
        stravaServices.processAuthorizationCode(code, state);

        return ResponseEntity.status(HttpStatus.FOUND).header("location", "").build();
    }
}
