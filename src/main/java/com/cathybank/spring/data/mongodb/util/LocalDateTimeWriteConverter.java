package com.cathybank.spring.data.mongodb.util;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeWriteConverter implements Converter<LocalDateTime, String> {
    @Override
    public String convert(LocalDateTime source) {
        return source.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
