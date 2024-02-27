package com.cathybank.spring.data.mongodb.type;

public enum Message {

    NO_USD_NTD("No USD and NTD dollar");

    private final String key;

    Message(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
