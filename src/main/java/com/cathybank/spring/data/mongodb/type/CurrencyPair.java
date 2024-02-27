package com.cathybank.spring.data.mongodb.type;

public enum CurrencyPair {

    DATE("Date"),
    USD_TO_NTD("USD/NTD"),
    RMB_TO_NTD("RMB/NTD"),
    EUR_TO_USD("EUR/USD"),
    USD_TO_JPY("USD/JPY"),
    GBP_TO_USD("GBP/USD"),
    AUD_TO_USD("AUD/USD"),
    USD_TO_HKD("USD/HKD"),
    USD_TO_RMB("USD/RMB"),
    USD_TO_ZAR("USD/ZAR"),
    NZD_TO_USD("NZD/USD");

    private final String key;

    CurrencyPair(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
