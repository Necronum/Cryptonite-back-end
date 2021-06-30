package com.cryptonite.cryptonite.domain.portfolio;

import lombok.*;

@Data
@Builder
public class PortfolioDto {
    private Long id;
    private Long userId;
    private String coinSymbol;
    private String quantity;
}
