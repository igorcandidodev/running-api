package com.runningapi.runningapi.enums;

public enum StatusActivity {

    PENDING("PENDING"),
    COMPLETED("COMPLETED");

    private final String status;

    StatusActivity(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

}
