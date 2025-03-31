package com.runningapi.runningapi.dto.strava.response.activity;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SimilarActivities(
        @JsonProperty("effort_count")
        int effortCount,

        @JsonProperty("average_speed")
        Double averageSpeed,

        @JsonProperty("min_average_speed")
        Double minAverageSpeed,

        @JsonProperty("mid_average_speed")
        Double midAverageSpeed,

        @JsonProperty("max_average_speed")
        Double maxAverageSpeed,

        @JsonProperty("pr_rank")
        Object prRank,

        @JsonProperty("frequency_milestone")
        Object frequencyMilestone,

        @JsonProperty("trend")
        Trend trend,

        @JsonProperty("resource_state")
        Integer resourceState
) {
}
