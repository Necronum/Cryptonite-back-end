package com.cryptonite.cryptonite.service;

import com.cryptonite.cryptonite.domain.cryptohistory.CryptoHistory;
import com.cryptonite.cryptonite.domain.cryptohistory.CryptoHistoryDto;
import com.cryptonite.cryptonite.exception.ResourceNotFoundException;
import com.cryptonite.cryptonite.mapper.CryptoHistoryMapper;
import com.cryptonite.cryptonite.repository.CryptoHistoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.CryptoPrimitive;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class CryptoHistoryService {
    private final CryptoHistoryRepository cryptoHistoryRepository;
    private final CryptoHistoryMapper cryptoHistoryMapper;

    public List<CryptoHistoryDto> getCryptoHistories(){
        List<CryptoHistory> cryptoHistories = cryptoHistoryRepository.findAll();
        return cryptoHistoryMapper.mapToCryptoHistoryDtoList(cryptoHistories);
    }

    public CryptoHistoryDto getCryptoHistoryById(Long id){
        return cryptoHistoryRepository.findById(id)
                .map(cryptoHistoryMapper::mapToCryptoHistoryDto)
                .orElseThrow(() -> new ResourceNotFoundException("Crypto log with id: " + id + " not found"));
    }

    public void addCryptoHistory(CryptoHistoryDto cryptoHistoryDto){
        CryptoHistory cryptoHistory = cryptoHistoryMapper.mapToCryptoHistory(cryptoHistoryDto);
        cryptoHistoryRepository.save(cryptoHistory);
    }
}
