package com.cathybank.spring.data.mongodb.config;

import com.cathybank.spring.data.mongodb.util.LocalDateTimeReadConverter;
import com.cathybank.spring.data.mongodb.util.LocalDateTimeWriteConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.time.LocalDateTime;
import java.util.Arrays;

@Configuration
public class MongoConfig {

    @Bean
    public MongoCustomConversions customConversions() {
        return new MongoCustomConversions(Arrays.asList(new LocalDateTimeReadConverter("yyyy-MM-dd HH:mm:ss"), new LocalDateTimeWriteConverter()));
    }
}
