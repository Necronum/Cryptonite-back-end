package com.cryptonite.cryptonite.domain.user;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class UserDto {
    private Long id;
    private String username;
    private String password;
    private String email;
    private BigDecimal wallet;
    private List<Long> portfolioIds;
    private List<Long> marketOfferIds;
    private List<Long> walletHistoryIds;
    private List<Long> purchaseMarketHistoryIds;
    private List<Long> sellMarketHistoryIds;
    private List<Long> cryptoHistoryIds;
}
