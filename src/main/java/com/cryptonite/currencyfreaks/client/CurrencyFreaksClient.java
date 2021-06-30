package com.cryptonite.currencyfreaks.client;

import com.cryptonite.coingecko.client.CoinGeckoClient;
import com.cryptonite.currencyfreaks.domain.CurrencyDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.*;

@Component
@RequiredArgsConstructor
public class CurrencyFreaksClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(CoinGeckoClient.class);
    private final RestTemplate restTemplate;
    private final CurrencyFreaksURL url;

    public CurrencyDto getRate(){
            return restTemplate.getForObject(url.ratesUrl(), CurrencyDto.class);
    }
}
