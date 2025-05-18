package com.runningapi.runningapi.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Frequency {
    ALWAYS("SEMPRE"),
    SOMETIMES("ÀS VEZES"),
    RARELY("RARAMENTE");

    private final String value;

    Frequency(String value) {
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
    public static Frequency fromValue(String value) {
        for (Frequency frequency : Frequency.values()) {
            if (frequency.value.equalsIgnoreCase(value)) {
                return frequency;
            }
        }
        throw new IllegalArgumentException("Valor inválido para Frequency: " + value);
    }
}
