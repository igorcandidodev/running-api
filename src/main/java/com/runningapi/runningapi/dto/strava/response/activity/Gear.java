package com.runningapi.runningapi.dto.strava.response.activity;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Gear(
        @JsonProperty("distance") Double distance,
        @JsonProperty("elapsed_time") Integer elapsedTime,
        @JsonProperty("elevation_difference") Double elevationDifference,
        @JsonProperty("moving_time") Integer movingTime,
        @JsonProperty("split") Integer split,
        @JsonProperty("average_speed") Double averageSpeed,
        @JsonProperty("pace_zone") Integer paceZone
) {
}
