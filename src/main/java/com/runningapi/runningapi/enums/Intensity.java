package com.runningapi.runningapi.enums;

public enum Intensity {
    FRACO("Fraco"),
    MODERADO("Moderado"),
    FORTE("Forte");

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
}
