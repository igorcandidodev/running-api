package com.runningapi.runningapi.dto.strava.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.ZonedDateTime;

public record AthleteResponse(

        @JsonProperty("id")
        Long id,

        @JsonProperty("username")
        String username,

        @JsonProperty("resource_state")
        String resourceState,

        @JsonProperty("firstname")
        String firstname,

        @JsonProperty("lastname")
        String lastname,

        @JsonProperty("bio")
        String bio,

        @JsonProperty("city")
        String city,

        @JsonProperty("state")
        String state,

        @JsonProperty("country")
        String country,

        @JsonProperty("sex")
        String sex,

        @JsonProperty("premium")
        String premium,

        @JsonProperty("summit")
        String summit,

        @JsonProperty("created_at")
        ZonedDateTime created_at,

        @JsonProperty("updated_at")
        ZonedDateTime updated_at,

        @JsonProperty("badge_type_id")
        int badge_type_id,

        @JsonProperty("weight")
        String weight,

        @JsonProperty("profile_medium")
        String profile_medium,

        @JsonProperty("profile")
        String profile,

        @JsonProperty("friend")
        String friend,

        @JsonProperty("follower")
        String follower

) {
}
