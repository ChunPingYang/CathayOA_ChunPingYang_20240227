package com.cathybank.spring.data.mongodb.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
    @Override
    public void serialize(LocalDateTime localDateTime, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        String formattedDateTime = localDateTime.format(FORMATTER);
        gen.writeString(formattedDateTime);
    }
}
