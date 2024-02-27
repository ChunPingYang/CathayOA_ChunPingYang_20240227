package com.cathybank.spring.data.mongodb.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorDetails{
    private String code;
    private String message;
}