package com.cryptonite.coingecko.client;

import com.cryptonite.coingecko.config.CoinGeckoConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class CoinsURL {
    private final CoinGeckoConfig coinGeckoConfig;

    public URI coinsUrl(CoinsEndpoints endpoints){
        return UriComponentsBuilder.fromHttpUrl(coinGeckoConfig.getCoinGeckoEndpoint() + endpoints.getEndpoint())
                .queryParam("vs_currency", "usd")
                .queryParam("order", "market_cap_desc")
                .build()
                .encode()
                .toUri();
    }
}
