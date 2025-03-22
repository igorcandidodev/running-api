package com.runningapi.runningapi.dto.strava.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserAuthenticationResponse(
        @JsonProperty("token_type")
        String tokenType,

        @JsonProperty("expires_at")
        int expiresAt,

        @JsonProperty("expires_in")
        int expiresIn,

        @JsonProperty("refresh_token")
        String refreshToken,

        @JsonProperty("access_token")
        String accessToken,

        @JsonProperty("athlete")
        AthleteResponse athleteResponse

) {
}
