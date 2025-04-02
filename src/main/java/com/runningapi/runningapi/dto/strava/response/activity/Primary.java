package com.runningapi.runningapi.dto.strava.response.activity;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Primary(
        @JsonProperty("primary") Primary primary,
        @JsonProperty("use_primary_photo") Boolean usePrimaryPhoto,
        @JsonProperty("count") Integer count
) {
}
