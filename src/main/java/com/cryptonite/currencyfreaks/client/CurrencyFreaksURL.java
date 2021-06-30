package com.cryptonite.currencyfreaks.client;

import com.cryptonite.currencyfreaks.config.CurrencyFreaksConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class CurrencyFreaksURL {
    private final CurrencyFreaksConfig currencyFreaksConfig;

    public URI ratesUrl(){
        return UriComponentsBuilder.fromHttpUrl(currencyFreaksConfig.getCurrencyFreaksEndpoint() + "latest")
                .queryParam("apikey",currencyFreaksConfig.getCurrencyFreaksApiKey())
                .build()
                .encode()
                .toUri();
    }
}
