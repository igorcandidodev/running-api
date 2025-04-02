package com.runningapi.runningapi.dto.strava.response.activity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record Segment(
        @JsonProperty("id") Long id,
        @JsonProperty("resource_state") Integer resourceState,
        @JsonProperty("name") String name,
        @JsonProperty("activity_type") String activityType,
        @JsonProperty("distance") Double distance,
        @JsonProperty("average_grade") Double averageGrade,
        @JsonProperty("maximum_grade") Double maximumGrade,
        @JsonProperty("elevation_high") Double elevationHigh,
        @JsonProperty("elevation_low") Double elevationLow,
        @JsonProperty("start_latlng") List<Double> startLatlng,
        @JsonProperty("end_latlng") List<Double> endLatlng,
        @JsonProperty("climb_category") Integer climbCategory,
        @JsonProperty("city") String city,
        @JsonProperty("state") String state,
        @JsonProperty("country") String country,
        @JsonProperty("private") Boolean isPrivate,
        @JsonProperty("hazardous") Boolean hazardous,
        @JsonProperty("starred") Boolean starred
) {
}
