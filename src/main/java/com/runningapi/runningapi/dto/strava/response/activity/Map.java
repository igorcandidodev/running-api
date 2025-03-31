package com.runningapi.runningapi.dto.strava.response.activity;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Map(
        @JsonProperty("id")
        String id,

        @JsonProperty("polyline")
        String polyline,

        @JsonProperty("resource_state")
        Integer resourceState,

        @JsonProperty("summary_polyline")
        String summaryPolyline
) {
}
