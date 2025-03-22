package com.runningapi.runningapi.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StravaConfig {

    @Value("${strava.client.id}")
    private String clientId;

    @Value("${strava.client.secret}")
    private String clientSecret;

    @Value("${strava.client.redirect_uri}")
    private String redirectUri;

    @Value("${strava.client.scope}")
    private String scope;

    @Value("${strava.client.auth_uri}")
    private String authUri;

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public String getScope() {
        return scope;
    }

    public String getAuthUri() {
        return authUri;
    }

}
