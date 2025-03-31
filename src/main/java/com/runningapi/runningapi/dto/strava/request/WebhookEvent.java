package com.runningapi.runningapi.dto.strava.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record WebhookEvent(

        @JsonProperty("event_time")
        Long eventTime,

        @JsonProperty("aspect_type")
        String aspectType,

        @JsonProperty("object_id")
        Long objectId,

        @JsonProperty("object_type")
        String objectType,

        @JsonProperty("owner_id")
        Long ownerId,

        @JsonProperty("subscription_id")
        Long subscriptionId,

        @JsonProperty("updates")
        WebhookUpdate updates

) {
}
