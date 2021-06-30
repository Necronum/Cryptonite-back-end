package com.cryptonite.cryptonite.mapper;

import com.cryptonite.cryptonite.domain.wallethistory.WalletHistory;
import com.cryptonite.cryptonite.domain.wallethistory.WalletHistoryDto;
import com.cryptonite.cryptonite.exception.ResourceNotFoundException;
import com.cryptonite.cryptonite.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class WalletHistoryMapper {
    private final UserRepository userRepository;

    public WalletHistory mapToWalletHistory(final WalletHistoryDto walletHistoryDto){
        return WalletHistory.builder()
                .id(walletHistoryDto.getId())
                .operationDate(walletHistoryDto.getOperationDate())
                .user(userRepository.findById(walletHistoryDto.getUserId())
                        .orElseThrow(() -> new ResourceNotFoundException("User with id: " + walletHistoryDto.getUserId() + " not found")))
                .walletChange(walletHistoryDto.getWalletChange())
                .build();
    }

    public WalletHistoryDto mapToWalletHistoryDto(final WalletHistory walletHistory){
        return WalletHistoryDto.builder()
                .id(walletHistory.getId())
                .operationDate(walletHistory.getOperationDate())
                .userId(walletHistory.getUser().getId())
                .walletChange(walletHistory.getWalletChange())
                .build();
    }

    public List<WalletHistory> mapToWalletHistoryList(final List<WalletHistoryDto> walletHistoryDtos){
        return walletHistoryDtos.stream()
                .map(this::mapToWalletHistory)
                .collect(Collectors.toList());
    }

    public List<WalletHistoryDto> mapToWalletHistoryDtoList(final List<WalletHistory> walletHistories){
        return walletHistories.stream()
                .map(this::mapToWalletHistoryDto)
                .collect(Collectors.toList());
    }
}
