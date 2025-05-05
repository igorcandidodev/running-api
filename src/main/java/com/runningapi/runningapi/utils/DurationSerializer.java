package com.runningapi.runningapi.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.Duration;

public class DurationSerializer extends JsonSerializer<Duration> {

    @Override
    public void serialize(Duration duration, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        long seconds = duration.getSeconds();
        long absSeconds = Math.abs(seconds);

        String formatted = String.format("%02d:%02d:%02d",
                absSeconds / 3600,
                (absSeconds % 3600) / 60,
                absSeconds % 60);

        gen.writeString(formatted);
    }
}
