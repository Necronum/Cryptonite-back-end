package com.cryptonite.cryptonite.mapper;

import com.cryptonite.cryptonite.domain.markethistory.MarketHistory;
import com.cryptonite.cryptonite.domain.markethistory.MarketHistoryDto;
import com.cryptonite.cryptonite.exception.ResourceNotFoundException;
import com.cryptonite.cryptonite.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MarketHistoryMapper {
    private final UserRepository userRepository;

    public MarketHistory mapToMarketHistory(final MarketHistoryDto marketHistoryDto){
        return MarketHistory.builder()
                .id(marketHistoryDto.getId())
                .operationDate(marketHistoryDto.getOperationDate())
                .purchasingUser(userRepository.findById(marketHistoryDto.getPurchasingUserId())
                        .orElseThrow(() -> new ResourceNotFoundException("User with id: " + marketHistoryDto.getPurchasingUserId() + " not found")))
                .purchasingCoinSymbol(marketHistoryDto.getPurchasingCoinSymbol())
                .purchasingQuantity(marketHistoryDto.getPurchasingQuantity())
                .sellingUser(userRepository.findById(marketHistoryDto.getSellingUserId())
                        .orElseThrow(() -> new ResourceNotFoundException("User with id: " + marketHistoryDto.getPurchasingUserId() + " not found")))
                .sellingCoinSymbol(marketHistoryDto.getSellingCoinSymbol())
                .sellingQuantity(marketHistoryDto.getSellingQuantity())
                .build();
    }

    public MarketHistoryDto mapToMarketHistoryDto(final MarketHistory marketHistory){
        return MarketHistoryDto.builder()
                .id(marketHistory.getId())
                .operationDate(marketHistory.getOperationDate())
                .purchasingUserId(marketHistory.getPurchasingUser().getId())
                .purchasingCoinSymbol(marketHistory.getPurchasingCoinSymbol())
                .purchasingQuantity(marketHistory.getPurchasingQuantity())
                .sellingUserId(marketHistory.getSellingUser().getId())
                .sellingCoinSymbol(marketHistory.getSellingCoinSymbol())
                .sellingQuantity(marketHistory.getSellingQuantity())
                .build();
    }

    public List<MarketHistory> mapToMarketHistoryList(final List<MarketHistoryDto> marketHistoryDtos){
        return marketHistoryDtos.stream()
                .map(this::mapToMarketHistory)
                .collect(Collectors.toList());
    }

    public List<MarketHistoryDto> mapToMarketHistoryDtoList(final List<MarketHistory> marketHistories){
        return marketHistories.stream()
                .map(this::mapToMarketHistoryDto)
                .collect(Collectors.toList());
    }
}
