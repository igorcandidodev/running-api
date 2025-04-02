package com.runningapi.runningapi.dto.strava.response.activity;

import com.fasterxml.jackson.annotation.JsonProperty;

public record StatsVisibility(
        @JsonProperty("id")
        Long id,

        @JsonProperty("resource_state")
        Integer resourceState
) {
}
