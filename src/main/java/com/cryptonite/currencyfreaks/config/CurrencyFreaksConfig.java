package com.cryptonite.currencyfreaks.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class CurrencyFreaksConfig {
    @Value("${currencyfreaks.api.endpoint}")
    private String currencyFreaksEndpoint;
    @Value("${currencyfreaks.api.key}")
    private String currencyFreaksApiKey;
}
