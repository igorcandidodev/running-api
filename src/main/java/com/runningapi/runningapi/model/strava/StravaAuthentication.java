package com.runningapi.runningapi.model.strava;

import jakarta.persistence.*;

@Entity
@Table(name = "strava_authentication")
public class StravaAuthentication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tokenType;
    private int expiresAt;
    private int expiresIn;
    private String refreshToken;
    private String accessToken;

    @OneToOne
    @JoinColumn(name = "athlete_id")
    private Athlete athlete;

    public StravaAuthentication() {
    }

    public StravaAuthentication(String tokenType, int expiresAt, int expiresIn, String refreshToken, String accessToken, Athlete athlete) {
        this.tokenType = tokenType;
        this.expiresAt = expiresAt;
        this.expiresIn = expiresIn;
        this.refreshToken = refreshToken;
        this.accessToken = accessToken;
        this.athlete = athlete;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public int getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(int expiresAt) {
        this.expiresAt = expiresAt;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Athlete getAthlete() {
        return athlete;
    }

    public void setAthlete(Athlete athlete) {
        this.athlete = athlete;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
