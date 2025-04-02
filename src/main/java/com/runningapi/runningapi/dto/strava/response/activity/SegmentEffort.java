package com.runningapi.runningapi.dto.strava.response.activity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.ZonedDateTime;
import java.util.List;

public record SegmentEffort(
        @JsonProperty("id") Long id,
        @JsonProperty("resource_state") Integer resourceState,
        @JsonProperty("name") String name,
        @JsonProperty("activity") Activity activity,
        @JsonProperty("athlete") Athlete athlete,
        @JsonProperty("elapsed_time") Integer elapsedTime,
        @JsonProperty("moving_time") Integer movingTime,
        @JsonProperty("start_date") ZonedDateTime startDate,
        @JsonProperty("start_date_local") ZonedDateTime startDateLocal,
        @JsonProperty("distance") Double distance,
        @JsonProperty("start_index") Integer startIndex,
        @JsonProperty("end_index") Integer endIndex,
        @JsonProperty("average_cadence") Double averageCadence,
        @JsonProperty("device_watts") Boolean deviceWatts,
        @JsonProperty("average_watts") Double averageWatts,
        @JsonProperty("segment") Segment segment,
        @JsonProperty("kom_rank") Object komRank,
        @JsonProperty("pr_rank") Object prRank,
        @JsonProperty("achievements") List<Object> achievements,
        @JsonProperty("hidden") Boolean hidden
) {
}
