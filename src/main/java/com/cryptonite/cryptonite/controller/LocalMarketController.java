package com.cryptonite.cryptonite.controller;

import com.cryptonite.cryptonite.domain.localmarket.LocalMarketDto;
import com.cryptonite.cryptonite.service.LocalMarketService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("v1/market")
public class LocalMarketController {
    private final LocalMarketService localMarketService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<LocalMarketDto> getAllOffers() {
        return localMarketService.getOffers();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LocalMarketDto getOfferById(@PathVariable Long id) {
        return localMarketService.getOfferById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addOffer(@RequestBody LocalMarketDto localMarketDto) {
        localMarketService.addOffer(localMarketDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public LocalMarketDto updateOffer(@PathVariable Long id, @RequestBody LocalMarketDto localMarketDto) {
        return localMarketService.updateOffer(id, localMarketDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeOrderById(@PathVariable Long id) {
        localMarketService.deleteOffer(id);
    }
}
