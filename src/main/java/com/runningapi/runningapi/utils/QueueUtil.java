package com.runningapi.runningapi.utils;

import java.util.Map;

public class QueueUtil {

    public static boolean checkRetryCount(Map<String, ?> xDeath, Long retryCount) {

        if (xDeath != null && !xDeath.isEmpty()) {
            Long count = (Long) xDeath.get("count");
            return count >= retryCount;
        }

        return false;
    }
}
