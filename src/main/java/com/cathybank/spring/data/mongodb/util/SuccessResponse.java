package com.cathybank.spring.data.mongodb.util;

import com.cathybank.spring.data.mongodb.model.CurrencyData;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SuccessResponse {

    private ErrorDetails error;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<CurrencyData> currency;

    public SuccessResponse(ErrorDetails error, List<CurrencyData>... currencyList){
        this.error = error;
        this.currency = (currencyList == null || currencyList.length == 0) ? null : currencyList[0];
    }
}
