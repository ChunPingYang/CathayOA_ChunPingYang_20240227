package com.cathybank.spring.data.mongodb.service;

import com.cathybank.spring.data.mongodb.model.CurrencyData;
import com.cathybank.spring.data.mongodb.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CurrencyServiceImpl implements CurrencyService{

    @Autowired
    private CurrencyRepository currencyRepository;

    @Override
    public List<Map<String,String>> fetchCurrencyDataFromApi() {

        String apiUrl = "https://openapi.taifex.com.tw/v1/DailyForeignExchangeRates";
        RestTemplate restTemplate = new RestTemplate();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(
                Arrays.asList(MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM));
        restTemplate.setMessageConverters(List.of(converter));
        ParameterizedTypeReference<List<Map<String,String>>> responseType = new ParameterizedTypeReference<>(){};
        ResponseEntity<List<Map<String,String>>> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.GET, null, responseType);
        return responseEntity.getBody();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String insertCurrencyData(Map<String, String> currencyExchangeRate) {
        if(!currencyExchangeRate.containsKey("USD/NTD")){
            return "No USD and NTD dollar";
        }

        String exchange = currencyExchangeRate.get("USD/NTD");
        CurrencyData data = new CurrencyData(exchange);
        currencyRepository.save(data);
        return "success";
    }

    @Override
    public List<CurrencyData> getExchangeRateByTimeInterval(LocalDateTime startDate, LocalDateTime endDate) {
        return currencyRepository.findByExchangeTimeBetween(startDate, endDate);
    }


}
