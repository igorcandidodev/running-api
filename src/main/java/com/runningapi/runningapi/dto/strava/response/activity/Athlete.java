package com.runningapi.runningapi.dto.strava.response.activity;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Athlete(
        @JsonProperty("id")
        Long id,

        @JsonProperty("resource_state")
        Integer resourceState
) {
}
