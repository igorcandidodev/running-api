package com.runningapi.runningapi.dto.strava.response.activity;

import com.fasterxml.jackson.annotation.JsonProperty;

public record HighlightedKudoser(
        @JsonProperty("primary") Primary primary,
        @JsonProperty("use_primary_photo") Boolean usePrimaryPhoto,
        @JsonProperty("count") Integer count
) {
}
