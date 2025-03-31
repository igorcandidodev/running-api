package com.runningapi.runningapi.dto.strava.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record WebhookUpdate(
        @JsonProperty("title")
        String title,

        @JsonProperty("type")
        String type,

        @JsonProperty("private")
        Boolean privateField
) {
}
