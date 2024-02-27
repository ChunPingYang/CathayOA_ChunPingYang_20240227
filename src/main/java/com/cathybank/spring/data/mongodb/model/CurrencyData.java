package com.cathybank.spring.data.mongodb.model;

import com.cathybank.spring.data.mongodb.util.LocalDateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.time.LocalDateTime;

@Getter
@Setter
@Document(collection = "currency_data")
public class CurrencyData {

    @Id
    @JsonIgnore
    private String id;

    @Field(name = "utd_ntd")
    private String usd;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Field(name = "exchange_time", targetType = FieldType.STRING)
    private LocalDateTime date;

    public CurrencyData(String usd) {
        this.date = LocalDateTime.now();
        this.usd = usd;
    }
}
