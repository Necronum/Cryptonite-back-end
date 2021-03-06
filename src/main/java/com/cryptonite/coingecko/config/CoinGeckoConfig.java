package com.cryptonite.coingecko.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class CoinGeckoConfig {
    @Value("${coingecko.api.endpoint}")
    private String coinGeckoEndpoint;
}
