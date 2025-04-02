package com.runningapi.runningapi.dto.strava.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CallbackResponse(
        @JsonProperty("hub.challenge")
        String hubChallenge
) {
}
