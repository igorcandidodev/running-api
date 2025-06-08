package com.runningapi.runningapi.model;

import jakarta.persistence.*;

import java.time.Duration;
import java.time.Instant;
import java.util.Random;

@Entity
@Table(name = "passwords_resets")
public class PasswordReset {

    private static final Duration EXPIRATION = Duration.ofMinutes(5);
    private static final Random RANDOM = new Random();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, updatable = false)
    private String code;

    @Column(nullable = false)
    private Instant expirationCodeTime;

    @Column(nullable = false)
    private int attempts = 0;


    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    public PasswordReset(String code, Instant expirationTokenTime, User user) {
        this.code = code;
        this.expirationCodeTime = expirationTokenTime;
        this.user = user;
    }

    public PasswordReset() {
    }

    public static String generateRandomCode() {
        int code = RANDOM.nextInt(900_000) + 100_000;
        return String.valueOf(code);
    }

    @PrePersist
    private void prePersist() {
        if (expirationCodeTime == null) {
            expirationCodeTime = calculateExpiryTime();
        }
        if (code == null) {
            code = generateRandomCode();
        }
    }

    public Instant calculateExpiryTime() {
        return Instant.now().plus(EXPIRATION);
    }

    public boolean isExpired() {
        return Instant.now().isAfter(expirationCodeTime);
    }

    public void incrementAttempts() {
        this.attempts++;
    }

    public int getAttempts() {
        return attempts;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Instant getExpirationCodeTime() {
        return expirationCodeTime;
    }

    public void setExpirationCodeTime(Instant expirationCodeTime) {
        this.expirationCodeTime = expirationCodeTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}