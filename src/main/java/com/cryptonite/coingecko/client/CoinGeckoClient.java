package com.cryptonite.coingecko.client;

import com.cryptonite.coingecko.domain.CoinDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CoinGeckoClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(CoinGeckoClient.class);
    private final RestTemplate restTemplate;
    private final CoinsURL url;

    public List<CoinDto> getCoinRates(){
        try{
            CoinDto[] coinResponse = restTemplate.getForObject(url.coinsUrl(CoinsEndpoints.COINS), CoinDto[].class);
            return new ArrayList<>(Optional.ofNullable(coinResponse)
                    .map(Arrays::asList)
                    .orElse(Collections.emptyList()));
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }

}
