package com.cryptonite.cryptonite.mapper;

import com.cryptonite.cryptonite.domain.cryptohistory.CryptoHistory;
import com.cryptonite.cryptonite.domain.localmarket.LocalMarket;
import com.cryptonite.cryptonite.domain.markethistory.MarketHistory;
import com.cryptonite.cryptonite.domain.portfolio.Portfolio;
import com.cryptonite.cryptonite.domain.user.User;
import com.cryptonite.cryptonite.domain.user.UserDto;
import com.cryptonite.cryptonite.domain.wallethistory.WalletHistory;
import com.cryptonite.cryptonite.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserMapper {
    private final LocalMarketRepository localMarketRepository;
    private final PortfolioRepository portfolioRepository;
    private final WalletHistoryRepository walletHistoryRepository;
    private final MarketHistoryRepository marketHistoryRepository;
    private final CryptoHistoryRepository cryptoHistoryRepository;

    public User mapToUser(UserDto userDto){
        return User.builder()
                .id(userDto.getId())
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .email(userDto.getEmail())
                .wallet(userDto.getWallet())
                .portfolios(portfolioRepository.findAll())
                .marketOffers(localMarketRepository.findAll())
                .walletHistories(walletHistoryRepository.findAll())
                .purchaseMarketHistories(marketHistoryRepository.findAll())
                .sellMarketHistories(marketHistoryRepository.findAll())
                .cryptoHistories(cryptoHistoryRepository.findAll())
                .build();
    }

    public UserDto mapToUserDto(User user){
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .wallet(user.getWallet())
                .portfolioIds(user.getPortfolios().stream()
                    .map(Portfolio::getId)
                    .collect(Collectors.toList()))
                .marketOfferIds(user.getMarketOffers().stream()
                    .map(LocalMarket::getId)
                    .collect(Collectors.toList()))
                .walletHistoryIds(user.getWalletHistories().stream()
                    .map(WalletHistory::getId)
                    .collect(Collectors.toList()))
                .purchaseMarketHistoryIds(user.getPurchaseMarketHistories().stream()
                        .map(MarketHistory::getId)
                        .collect(Collectors.toList()))
                .sellMarketHistoryIds(user.getPurchaseMarketHistories().stream()
                        .map(MarketHistory::getId)
                        .collect(Collectors.toList()))
                .cryptoHistoryIds(user.getCryptoHistories().stream()
                        .map(CryptoHistory::getId)
                        .collect(Collectors.toList()))
                .build();
    }

    public List<User> mapToUserList(List<UserDto> usersDto){
        return usersDto
                .stream()
                .map(this::mapToUser)
                .collect(Collectors.toList());
    }

    public List<UserDto> mapToUserDtoList(List<User> users){
        return users
                .stream()
                .map(this::mapToUserDto)
                .collect(Collectors.toList());
    }
}
