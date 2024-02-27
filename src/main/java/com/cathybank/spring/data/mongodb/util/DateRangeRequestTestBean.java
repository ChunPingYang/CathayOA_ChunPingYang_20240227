package com.cathybank.spring.data.mongodb.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class DateRangeRequestTestBean {

    private String startDate;
    private String endDate;
    private String currency;
}
