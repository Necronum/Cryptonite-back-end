package com.cryptonite.cryptonite.mapper;

import com.cryptonite.cryptonite.domain.localmarket.LocalMarket;
import com.cryptonite.cryptonite.domain.localmarket.LocalMarketDto;
import com.cryptonite.cryptonite.exception.ResourceNotFoundException;
import com.cryptonite.cryptonite.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LocalMarketMapper {
    private final UserRepository userRepository;

    public LocalMarket mapToLocalMarket(final LocalMarketDto localMarketDto){
        return LocalMarket.builder()
                .id(localMarketDto.getId())
                .user(userRepository.findById(localMarketDto.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User with id: " + localMarketDto.getUserId() + " not found")))
                .coinSymbol(localMarketDto.getCoinSymbol())
                .quantity(localMarketDto.getQuantity())
                .coinSymbolFor(localMarketDto.getCoinSymbolFor())
                .quantityFor(localMarketDto.getQuantityFor())
                .build();
    }

    public LocalMarketDto mapToLocalMarketDto (final LocalMarket localMarket){
        return LocalMarketDto.builder()
                .id(localMarket.getId())
                .userId(localMarket.getUser().getId())
                .coinSymbol(localMarket.getCoinSymbol())
                .quantity(localMarket.getQuantity())
                .coinSymbolFor(localMarket.getCoinSymbolFor())
                .quantityFor(localMarket.getQuantityFor())
                .build();
    }

    public List<LocalMarket> mapToLocalMarketList (final List<LocalMarketDto> localMarketDtoList){
        return localMarketDtoList.stream()
                .map(this::mapToLocalMarket)
                .collect(Collectors.toList());
    }

    public List<LocalMarketDto> mapToLocalMarketDtoList (final List<LocalMarket> localMarketList){
        return localMarketList.stream()
                .map(this::mapToLocalMarketDto)
                .collect(Collectors.toList());
    }
}
