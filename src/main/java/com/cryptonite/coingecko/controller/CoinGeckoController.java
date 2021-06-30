package com.cryptonite.coingecko.controller;

import com.cryptonite.coingecko.domain.CoinDto;
import com.cryptonite.coingecko.service.CoinGeckoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/v1/currencies")
@RequiredArgsConstructor
public class CoinGeckoController {
    @Autowired
    private CoinGeckoService coinGeckoService;

    @RequestMapping(method = RequestMethod.GET)
    public List<CoinDto> getCoins(){
        return coinGeckoService.fetchCoins();
    }

}
