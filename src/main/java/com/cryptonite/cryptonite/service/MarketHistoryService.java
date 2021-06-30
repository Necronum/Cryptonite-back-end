package com.cryptonite.cryptonite.service;

import com.cryptonite.cryptonite.domain.markethistory.MarketHistory;
import com.cryptonite.cryptonite.domain.markethistory.MarketHistoryDto;
import com.cryptonite.cryptonite.exception.ResourceNotFoundException;
import com.cryptonite.cryptonite.mapper.MarketHistoryMapper;
import com.cryptonite.cryptonite.repository.MarketHistoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class MarketHistoryService {
    private final MarketHistoryRepository marketHistoryRepository;
    private final MarketHistoryMapper marketHistoryMapper;

    public List<MarketHistoryDto> getMarketHistories(){
        List<MarketHistory> cryptoHistories = marketHistoryRepository.findAll();
        return marketHistoryMapper.mapToMarketHistoryDtoList(cryptoHistories);
    }

    public MarketHistoryDto getMarketHistory(Long id){
        return marketHistoryRepository.findById(id)
                .map(marketHistoryMapper::mapToMarketHistoryDto)
                .orElseThrow(() -> new ResourceNotFoundException("Market log with id: " + id + " not found"));
    }

    public void addMarketHistory(MarketHistoryDto marketHistoryDto){
        MarketHistory marketHistory = marketHistoryMapper.mapToMarketHistory(marketHistoryDto);
        marketHistoryRepository.save(marketHistory);
    }
}
