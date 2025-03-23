package com.runningapi.runningapi.model.strava;

import com.runningapi.runningapi.model.User;
import jakarta.persistence.*;

@Entity
@Table(name = "strava_authentication")
public class StravaAuthentication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tokenType;

    private Integer expiresAt;

    private Integer expiresIn;

    private String refreshToken;

    private String accessToken;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "athlete_id")
    private Athlete athlete;

    public StravaAuthentication(String tokenType, Integer expiresAt, Integer expiresIn, String refreshToken, String accessToken, User user, Athlete athlete) {
        this.tokenType = tokenType;
        this.expiresAt = expiresAt;
        this.expiresIn = expiresIn;
        this.refreshToken = refreshToken;
        this.accessToken = accessToken;
        this.user = user;
        this.athlete = athlete;
    }

    public StravaAuthentication() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public Integer getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Integer expiresAt) {
        this.expiresAt = expiresAt;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Integer expiresIn) {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Athlete getAthlete() {
        return athlete;
    }

    public void setAthlete(Athlete athlete) {
        this.athlete = athlete;
    }
}
