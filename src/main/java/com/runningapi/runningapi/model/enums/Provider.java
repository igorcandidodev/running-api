package com.runningapi.runningapi.model.enums;

public enum Provider {
    GOOGLE("GOOGLE"),
    LOCAL("LOCAL"),
    STRAVA("STRAVA");

    private final String value;

    Provider(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
