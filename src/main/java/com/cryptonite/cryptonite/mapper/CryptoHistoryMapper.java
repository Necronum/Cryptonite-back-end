package com.cryptonite.cryptonite.mapper;

import com.cryptonite.cryptonite.domain.cryptohistory.CryptoHistory;
import com.cryptonite.cryptonite.domain.cryptohistory.CryptoHistoryDto;
import com.cryptonite.cryptonite.domain.cryptohistory.OperationStatus;
import com.cryptonite.cryptonite.exception.ResourceNotFoundException;
import com.cryptonite.cryptonite.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CryptoHistoryMapper {
    private final UserRepository userRepository;

    public CryptoHistory mapToCryptoHistory(final CryptoHistoryDto cryptoHistoryDto){
        return CryptoHistory.builder()
                .id(cryptoHistoryDto.getId())
                .operationDate(cryptoHistoryDto.getOperationDate())
                .user(userRepository.findById(cryptoHistoryDto.getUserId())
                        .orElseThrow(() -> new ResourceNotFoundException("User with idL " + cryptoHistoryDto.getUserId() + " not found")))
                .coinSymbol(cryptoHistoryDto.getCoinSymbol())
                .quantity(cryptoHistoryDto.getQuantity())
                .operationStatus(OperationStatus.parseString(cryptoHistoryDto.getOperationStatus())
                        .orElseThrow(() -> new IllegalArgumentException("Unknown operation status")))
                .build();
    }

    public CryptoHistoryDto mapToCryptoHistoryDto(final CryptoHistory cryptoHistory){
        return CryptoHistoryDto.builder()
                .id(cryptoHistory.getId())
                .operationDate(cryptoHistory.getOperationDate())
                .userId(cryptoHistory.getUser().getId())
                .coinSymbol(cryptoHistory.getCoinSymbol())
                .quantity(cryptoHistory.getQuantity())
                .operationStatus(cryptoHistory.getOperationStatus().toString())
                .build();
    }

    public List<CryptoHistory> mapToCryptoHistoryList(List<CryptoHistoryDto> cryptoHistoryDtos){
        return cryptoHistoryDtos.stream()
                .map(this::mapToCryptoHistory)
                .collect(Collectors.toList());
    }

    public List<CryptoHistoryDto> mapToCryptoHistoryDtoList(List<CryptoHistory> cryptoHistories){
        return cryptoHistories.stream()
                .map(this::mapToCryptoHistoryDto)
                .collect(Collectors.toList());
    }
}
