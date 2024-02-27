package com.cathybank.spring.data.mongodb.repository;

import com.cathybank.spring.data.mongodb.model.CurrencyData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface CurrencyRepository extends MongoRepository<CurrencyData,String> {

    @Query("{'date': {'$gte': ?0, '$lte': ?1}}")
    List<CurrencyData> findByExchangeTimeBetween(LocalDateTime startDate, LocalDateTime endDate);
}
