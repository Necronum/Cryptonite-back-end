package com.cryptonite.currencyfreaks.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RatesDto {
    @JsonProperty("currencyCode")
    private String currencyCode;
    @JsonProperty("rate")
    private BigDecimal rate;
}
