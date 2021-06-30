package com.cryptonite.cryptonite.domain.markethistory;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class MarketHistoryDto {
    private Long id;
    private LocalDateTime operationDate;
    private Long purchasingUserId;
    private String purchasingCoinSymbol;
    private String purchasingQuantity;
    private Long sellingUserId;
    private String sellingCoinSymbol;
    private String sellingQuantity;
}
