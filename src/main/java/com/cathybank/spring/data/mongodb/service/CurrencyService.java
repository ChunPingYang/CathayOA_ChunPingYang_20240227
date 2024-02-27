package com.cathybank.spring.data.mongodb.service;

import com.cathybank.spring.data.mongodb.model.CurrencyData;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public interface CurrencyService {

    List<Map<String,String>> fetchCurrencyDataFromApi();

    String insertCurrencyData(Map<String,String> currencyExchangeRate);

    List<CurrencyData> getExchangeRateByTimeInterval(LocalDateTime startDate, LocalDateTime endDate);
}
