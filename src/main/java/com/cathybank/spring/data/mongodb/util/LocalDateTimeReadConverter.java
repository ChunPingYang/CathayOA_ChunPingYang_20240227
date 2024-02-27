package com.cathybank.spring.data.mongodb.util;

import org.springframework.core.convert.converter.Converter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeReadConverter implements Converter<String, LocalDateTime> {

    private final DateTimeFormatter formatter;

    public LocalDateTimeReadConverter(String format) {
        this.formatter = DateTimeFormatter.ofPattern(format);
    }

    @Override
    public LocalDateTime convert(String source) {
        return LocalDateTime.parse(source, formatter);
    }
}
