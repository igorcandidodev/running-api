package com.runningapi.runningapi.utils;

import org.apache.commons.text.StringSubstitutor;

import java.util.Map;

public class PromptTemplateUtil {

    public static String replacePlaceholders(String userPrompt, Map<String, Object> values) {
        StringSubstitutor substitutor = new StringSubstitutor(values);
        return substitutor.replace(userPrompt);
    }
}
