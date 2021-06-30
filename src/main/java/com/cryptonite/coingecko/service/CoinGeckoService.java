package com.cryptonite.coingecko.service;

import com.cryptonite.coingecko.client.CoinGeckoClient;
import com.cryptonite.coingecko.domain.CoinDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CoinGeckoService {
    private final CoinGeckoClient coinGeckoClient;

    public List<CoinDto> fetchCoins(){
        return coinGeckoClient.getCoinRates();
    }
}
