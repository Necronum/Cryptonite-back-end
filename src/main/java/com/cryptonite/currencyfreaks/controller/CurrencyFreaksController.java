package com.cryptonite.currencyfreaks.controller;

import com.cryptonite.currencyfreaks.domain.CurrencyDto;
import com.cryptonite.currencyfreaks.service.CurrencyFreaksService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/v1/exchange")
@RequiredArgsConstructor
public class CurrencyFreaksController {
    @Autowired
    private CurrencyFreaksService currencyFreaksService;

    @RequestMapping(method = RequestMethod.GET)
    public CurrencyDto getRate(){
        return currencyFreaksService.fetchRate();
    }
}
