package com.runningapi.runningapi.model.enums;

public enum StatusObjective {

    ACTIVE("ATIVO"),
    CANCELED("CANCELADO"),
    COMPLETED("CONCLUÍDO");

    private final String status;

    StatusObjective(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
