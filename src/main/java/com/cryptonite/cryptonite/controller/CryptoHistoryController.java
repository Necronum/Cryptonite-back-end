package com.cryptonite.cryptonite.controller;

import com.cryptonite.cryptonite.domain.cryptohistory.CryptoHistoryDto;
import com.cryptonite.cryptonite.service.CryptoHistoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("v1/crypto/history")
public class CryptoHistoryController {
    private final CryptoHistoryService cryptoHistoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CryptoHistoryDto> getAllCryptoHistory() {
        return cryptoHistoryService.getCryptoHistories();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CryptoHistoryDto getCryptoHistoryById(@PathVariable Long id) {
        return cryptoHistoryService.getCryptoHistoryById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addCryptoHistory(@RequestBody CryptoHistoryDto cryptoHistoryDto) {
        cryptoHistoryService.addCryptoHistory(cryptoHistoryDto);
    }
}
