package com.example.accessingdatamongodb.controller;

import com.cathybank.spring.data.mongodb.AccessingDataMongodbApplication;
import com.cathybank.spring.data.mongodb.dto.DateRangeRequest;
import com.cathybank.spring.data.mongodb.exception.InValidDateRangeException;
import com.cathybank.spring.data.mongodb.service.CurrencyService;
import com.cathybank.spring.data.mongodb.type.Message;
import com.cathybank.spring.data.mongodb.util.DateRangeRequestTestBean;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = AccessingDataMongodbApplication.class)
@AutoConfigureMockMvc
public class MyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private CurrencyService currencyService;

    @Test
    public void CurrencyController_FetchAndSearchCurrencyData_ReturnSuccess() throws Exception{

        when(currencyService.insertCurrencyData(any())).thenReturn("success");
        String message = currencyService.insertCurrencyData(any());
        verify(currencyService, times(1)).insertCurrencyData(any());
        // Assert the result
        assertEquals("success", message);


        List<Map<String, String>> currencyDataList = Arrays.asList(new HashMap<>());
        when(currencyService.fetchCurrencyDataFromApi()).thenReturn(currencyDataList);

        List<Map<String, String>> resultList = currencyService.fetchCurrencyDataFromApi();
        verify(currencyService, times(1)).fetchCurrencyDataFromApi();

        System.out.println(resultList.size());
        System.out.println(currencyDataList.size());
        assertIterableEquals(currencyDataList, resultList);

        mockMvc.perform(get("/currency/currency_data_today"))
                .andExpect(status().isOk());
    }

    @Test
    public void CurrencyController_FetchAndSearchCurrencyData_ReturnError() throws Exception {

        // Mock the behavior of the currencyService for an error scenario
        Map<String,String> map = new HashMap<>();
        map.put("XXX","30");
        when(currencyService.insertCurrencyData(map)).thenReturn(Message.NO_USD_NTD.getKey());

        String message = currencyService.insertCurrencyData(map);

        verify(currencyService, times(1)).insertCurrencyData(map);

        // Assert the result
        assertEquals("No USD and NTD dollar", message);

    }

    @Test
    public void CurrencyController_SearchCurrencyOtherThenUsd_ReturnBadRequest() throws Exception{
        LocalDate currentDate = LocalDate.now();
        LocalDate endDate = currentDate.minusDays(1);
        LocalDate startDate = currentDate.minusWeeks(5);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

        // Format the LocalDate as a String
        String _startDate = startDate.format(formatter);
        String _endDate = endDate.format(formatter);

        DateRangeRequestTestBean request = new DateRangeRequestTestBean(_startDate,_endDate, "usx");
        // Convert the object to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/currency/exchange_rate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                        .andExpect(status().isBadRequest());
    }

    @Test
    public void CurrencyController_QueryInvalidDateRange_ReturnInValidDateRangeException() throws Exception{
        LocalDate currentDate = LocalDate.now();
        LocalDate endDate = currentDate.minusDays(1);
        LocalDate startDate = currentDate.minusYears(2);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

        // Format the LocalDate as a String
        String _startDate = startDate.format(formatter);
        String _endDate = endDate.format(formatter);

        DateRangeRequestTestBean request = new DateRangeRequestTestBean(_startDate,_endDate, "usd");
        // Convert the object to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/currency/exchange_rate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void CurrencyController_QueryValidDateRange_ReturnSuccess() throws Exception{

        LocalDate currentDate = LocalDate.now();
        LocalDate endDate = currentDate.minusDays(1);
        LocalDate startDate = currentDate.minusWeeks(5);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

        // Format the LocalDate as a String
        String _startDate = startDate.format(formatter);
        String _endDate = endDate.format(formatter);

        DateRangeRequestTestBean request = new DateRangeRequestTestBean(_startDate,_endDate, "usd");
        // Convert the object to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/currency/exchange_rate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());
    }
}
