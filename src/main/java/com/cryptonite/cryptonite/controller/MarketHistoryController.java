package com.cryptonite.cryptonite.controller;

import com.cryptonite.cryptonite.domain.markethistory.MarketHistoryDto;
import com.cryptonite.cryptonite.service.MarketHistoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("v1/market/history")
public class MarketHistoryController {
    private final MarketHistoryService marketHistoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<MarketHistoryDto> getALlMarketHistory() {
        return marketHistoryService.getMarketHistories();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MarketHistoryDto getMarketHistoryById(@PathVariable Long id) {
        return marketHistoryService.getMarketHistory(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addMarketHistory(@RequestBody MarketHistoryDto marketHistoryDto) {
        marketHistoryService.addMarketHistory(marketHistoryDto);
    }
}
