package com.runningapi.runningapi.utils;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class DateConverter {

    public static ZonedDateTime convertToZonedDateTime(Integer timestamp) {
        if (timestamp == null) {
            return null;
        }
        return ZonedDateTime.ofInstant(Instant.ofEpochSecond(timestamp), ZoneId.systemDefault());
    }

    public static String convertToString(ZonedDateTime zonedDateTime, String format) {
        if (zonedDateTime == null) {
            return null;
        }
        return zonedDateTime.format(java.time.format.DateTimeFormatter.ofPattern(format));
    }
}
