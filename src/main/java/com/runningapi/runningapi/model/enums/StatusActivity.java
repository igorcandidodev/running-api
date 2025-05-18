package com.runningapi.runningapi.model.enums;

public enum StatusActivity {

    PENDING("PENDING"),
    CANCELED("CANCELED"),
    COMPLETED("COMPLETED");

    private final String status;

    StatusActivity(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

}
