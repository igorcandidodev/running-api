package com.runningapi.runningapi.model.google;

import com.runningapi.runningapi.model.User;
import jakarta.persistence.*;

@Entity(name = "google_authentication")
public class GoogleAuthentication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tokenType;

    private Long expiresIn;

    private String refreshToken;

    private String accessToken;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public GoogleAuthentication() {
    }

    public GoogleAuthentication(Long id, String tokenType, Long expiresIn, String refreshToken, String accessToken, User user) {
        this.id = id;
        this.tokenType = tokenType;
        this.expiresIn = expiresIn;
        this.refreshToken = refreshToken;
        this.accessToken = accessToken;
        this.user = user;
    }

    public GoogleAuthentication(Long id, String tokenType, Long expiresIn, String refreshToken, String accessToken) {
        this.id = id;
        this.tokenType = tokenType;
        this.expiresIn = expiresIn;
        this.refreshToken = refreshToken;
        this.accessToken = accessToken;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
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
}