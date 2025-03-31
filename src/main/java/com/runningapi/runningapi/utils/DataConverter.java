package com.runningapi.runningapi.utils;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class DataConverter {

    public static ZonedDateTime convertToZonedDateTime(Integer timestamp) {
        if (timestamp == null) {
            return null;
        }
        return ZonedDateTime.ofInstant(Instant.ofEpochSecond(timestamp), ZoneId.systemDefault());
    }
}
