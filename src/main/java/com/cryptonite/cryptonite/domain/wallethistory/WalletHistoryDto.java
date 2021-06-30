package com.cryptonite.cryptonite.domain.wallethistory;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class WalletHistoryDto {
    private Long id;
    private LocalDateTime operationDate;
    private Long userId;
    private BigDecimal walletChange;
}
