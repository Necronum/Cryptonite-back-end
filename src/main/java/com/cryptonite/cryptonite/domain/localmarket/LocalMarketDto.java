package com.cryptonite.cryptonite.domain.localmarket;

import com.sun.istack.NotNull;
import lombok.*;

@Data
@Builder
public class LocalMarketDto {
    private Long id;
    private Long userId;
    private String coinSymbol;
    private String quantity;
    private String coinSymbolFor;
    private String quantityFor;
}
