package com.runningapi.runningapi.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.runningapi.runningapi.exceptions.TokenGenerationException;
import com.runningapi.runningapi.exceptions.TokenInvalidException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Component
public class JWTToken {

    @Value("${spring.security.secret.token}")
    private String secret;

    public String generateToken(UserDetailsSecurity user) {
        try {
            var algorithm = Algorithm.HMAC256(secret);

            return JWT.create()
                    .withIssuer("Moove API")
                    .withSubject(user.getUsername())
                    .withExpiresAt(getTokenExpiration())
                    .sign(algorithm);

        } catch (JWTCreationException e) {
            throw new TokenGenerationException("Error creating JWT token: " + e.getMessage());
        }
    }

    public String validateToken(String token) {
        try {
            var algorithm = Algorithm.HMAC256(secret);

            return JWT.require(algorithm)
                    .withIssuer("Moove API")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
            throw new TokenInvalidException("Invalid token: " + e.getMessage());
        }
    }

    private Instant getTokenExpiration() {
        return LocalDateTime.now().plusHours(1).toInstant(ZoneOffset.of("-03:00"));
    }
}
