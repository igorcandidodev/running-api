package com.runningapi.runningapi.enums;

public enum Frequency {
    ALWAYS("Sempre"),
    SOMETIMES("Às vezes"),
    NEVER("Nunca");

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
}
