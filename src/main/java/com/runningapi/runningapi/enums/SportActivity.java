package com.runningapi.runningapi.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum SportActivity {
    RUNNING("CORRIDA"),
    GYMGOER("MUSCULAÇÃO"),
    SWIMMING("NATAÇÃO"),
    FIGHTING("LUTA"),
    SOCCER("FUTEBOL"),
    CYCLING("CICLISMO"),
    OTHER("OUTRO");

    private final String value;

    SportActivity(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    @JsonCreator
    public static SportActivity fromValue(String value) {
        for (SportActivity activity : SportActivity.values()) {
            if (activity.value.equalsIgnoreCase(value)) {
                return activity;
            }
        }
        throw new IllegalArgumentException("Valor inválido para SportActivity: " + value);
    }
}