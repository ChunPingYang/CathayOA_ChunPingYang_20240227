package com.cathybank.spring.data.mongodb.controller;

import com.cathybank.spring.data.mongodb.dto.DateRangeRequest;
import com.cathybank.spring.data.mongodb.exception.InValidDateRangeException;
import com.cathybank.spring.data.mongodb.model.CurrencyData;
import com.cathybank.spring.data.mongodb.service.CurrencyService;
import com.cathybank.spring.data.mongodb.type.Message;
import com.cathybank.spring.data.mongodb.util.ErrorDetails;
import com.cathybank.spring.data.mongodb.util.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/currency")
@EnableScheduling
@Configuration
public class CurrencyController {

    @Autowired
    private CurrencyService currencyService;

    //@Scheduled(cron = "0 0 18 * * *")
    @GetMapping("/currency_data_today")
    public ResponseEntity<String> fetchAndInsertCurrencyData(){
        List<Map<String,String>> list = currencyService.fetchCurrencyDataFromApi();
        Map<String,String> latestExchangeRate = list.get(list.size()-1);
        String message = currencyService.insertCurrencyData(latestExchangeRate);
        if(Message.NO_USD_NTD.getKey().equals(message)){
            return new ResponseEntity<>(message, HttpStatus.SERVICE_UNAVAILABLE);
        }
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @PostMapping("/exchange_rate")
    public ResponseEntity<SuccessResponse> getExchangeRateByDate(@RequestBody DateRangeRequest dateRangeRequest) {
        if(!dateRangeRequest.getCurrency().equals("usd")){
            SuccessResponse successResponse = new SuccessResponse(new ErrorDetails("E002","請選擇美金"));
            return new ResponseEntity<>(successResponse, HttpStatus.BAD_REQUEST);
        }
        validateDateRange(dateRangeRequest.getStartDate(), dateRangeRequest.getEndDate());

        LocalDateTime startDateTime = dateRangeRequest.getStartDate().atStartOfDay();
        LocalDateTime endDateTime = dateRangeRequest.getEndDate().plusDays(1).atStartOfDay().minusSeconds(1);

        List<CurrencyData> result = currencyService.getExchangeRateByTimeInterval(startDateTime, endDateTime);

        SuccessResponse successResponse = new SuccessResponse(new ErrorDetails("0000","成功"), result);
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    private void validateDateRange(LocalDate startDate, LocalDate endDate){
        LocalDate oneYearAgo = LocalDate.now().minusYears(1);

        if(startDate.isBefore(oneYearAgo) || endDate.isAfter(LocalDate.now().minusDays(1))){
            throw new InValidDateRangeException(new ErrorDetails("E001","日期區間不符"));
        }
    }
}
