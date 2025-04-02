package com.runningapi.runningapi.dto.strava.response.activity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record Trend(
        @JsonProperty("speeds")
        List<Double> speeds,

        @JsonProperty("current_activity_index")
        Object currentActivityIndex,

        @JsonProperty("min_speed")
        Double minSpeed,

        @JsonProperty("mid_speed")
        Double midSpeed,

        @JsonProperty("max_speed")
        Double maxSpeed,

        @JsonProperty("direction")
        Integer direction
) {
}
