package com.runningapi.runningapi.enums;

public enum SportActivity {
    CORRIDA("Corrida"),
    MUSCULACAO("Musculação"),
    NATACAO("Natação"),
    LUTA("Luta"),
    FUTEBOL("Futebol"),
    CICLISMO("Ciclismo"),
    OUTRO("Outro");

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
}
