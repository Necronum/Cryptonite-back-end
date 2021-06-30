package com.cryptonite.cryptonite.domain.cryptohistory;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CryptoHistoryDto {
    private Long id;
    private LocalDateTime operationDate;
    private Long userId;
    private String coinSymbol;
    private String quantity;
    private String operationStatus;
}
