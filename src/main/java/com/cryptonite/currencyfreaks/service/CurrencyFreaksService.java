package com.cryptonite.currencyfreaks.service;

import com.cryptonite.currencyfreaks.client.CurrencyFreaksClient;
import com.cryptonite.currencyfreaks.domain.CurrencyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrencyFreaksService {
    private final CurrencyFreaksClient currencyFreaksClient;

    public CurrencyDto fetchRate(){
        return currencyFreaksClient.getRate();
    }
}
