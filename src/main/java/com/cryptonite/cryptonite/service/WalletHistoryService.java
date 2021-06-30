package com.cryptonite.cryptonite.service;

import com.cryptonite.cryptonite.domain.wallethistory.WalletHistory;
import com.cryptonite.cryptonite.domain.wallethistory.WalletHistoryDto;
import com.cryptonite.cryptonite.exception.ResourceNotFoundException;
import com.cryptonite.cryptonite.mapper.WalletHistoryMapper;
import com.cryptonite.cryptonite.repository.WalletHistoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class WalletHistoryService {
    private final WalletHistoryRepository walletHistoryRepository;
    private final WalletHistoryMapper walletHistoryMapper;

    public List<WalletHistoryDto> getWalletHistories(){
        List<WalletHistory> walletHistories = walletHistoryRepository.findAll();
        return walletHistoryMapper.mapToWalletHistoryDtoList(walletHistories);
    }

    public WalletHistoryDto getWalletHistory(Long id){
        return walletHistoryRepository.findById(id)
                .map(walletHistoryMapper::mapToWalletHistoryDto)
                .orElseThrow(() -> new ResourceNotFoundException("Wallet log with id: " + id + " not found"));
    }

    public void addWalletHistory(WalletHistoryDto walletHistoryDto){
        WalletHistory walletHistory = walletHistoryMapper.mapToWalletHistory(walletHistoryDto);
        walletHistoryRepository.save(walletHistory);
    }
}
