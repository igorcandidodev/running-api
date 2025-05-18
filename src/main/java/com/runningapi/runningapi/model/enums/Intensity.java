package com.runningapi.runningapi.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Intensity {
    WEAK("FRACO"),
    MEDIUM("MODERADO"),
    STRONG("FORTE");

    private final String value;

    Intensity(String value) {
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
    public static Intensity fromValue(String value) {
        for (Intensity intensity : Intensity.values()) {
            if (intensity.value.equalsIgnoreCase(value)) {
                return intensity;
            }
        }
        throw new IllegalArgumentException("Valor inválido para Intensity: " + value);
    }
}
