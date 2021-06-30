package com.cryptonite.cryptonite.controller;

import com.cryptonite.cryptonite.domain.wallethistory.WalletHistoryDto;
import com.cryptonite.cryptonite.service.WalletHistoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("v1/wallet/history")
public class WalletHistoryController {
    private final WalletHistoryService walletHistoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<WalletHistoryDto> getAllWalletHistory() {
        return walletHistoryService.getWalletHistories();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public WalletHistoryDto getWalletHistoryById(@PathVariable Long id) {
        return walletHistoryService.getWalletHistory(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addWalletHistory(@RequestBody WalletHistoryDto walletHistoryDto) {
        walletHistoryService.addWalletHistory(walletHistoryDto);
    }
}
